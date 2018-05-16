package com.odauday.ui.propertydetail;

import static com.odauday.config.Constants.Task.TASK_GET_DETAIL_PROPERTY;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.DirectionRepository;
import com.odauday.data.local.cache.DirectionsPreference;
import com.odauday.model.MyPhone;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.propertydetail.common.SelectPhoneCallDialog;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.RowControllerListener;
import com.odauday.ui.propertydetail.rowdetails.bedbathpark.BedBathParkingRow;
import com.odauday.ui.propertydetail.rowdetails.description.DescriptionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.direction.DirectionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.gallery.GalleryDetailRow;
import com.odauday.ui.propertydetail.rowdetails.map.MapDetailRow;
import com.odauday.ui.propertydetail.rowdetails.note.NoteDetailRow;
import com.odauday.ui.propertydetail.rowdetails.tag.TagDetailRow;
import com.odauday.ui.propertydetail.rowdetails.vital.VitalDetailRow;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.TextUtils;
import com.odauday.utils.permissions.PermissionCallBack;
import com.odauday.utils.permissions.PermissionHelper;
import com.odauday.utils.permissions.PermissionHelper.Permission;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by infamouSs on 5/7/18.
 */
@SuppressLint("MissingPermission")
public class PropertyDetailActivity extends BaseMVVMActivity implements RowControllerListener,
                                                                        PropertyDetailContract {
    
    
    public static final String TAG = PropertyDetailActivity.class.getSimpleName();
    
    private RecyclerView mRecyclerView;
    private List<BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> mDefaultRows;
    private LinearLayoutManager mLayoutManager;
    
    private PropertyDetailRowAdapter mAdapter;
    
    @Inject
    DirectionsPreference mDirectionsPreference;
    
    @Inject
    DirectionRepository mDirectionRepository;
    
    @Inject
    PropertyDetailViewModel mPropertyDetailViewModel;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_full_detail;
    }
    
    private PropertyDetail mPropertyDetail;
    private GalleryDetailRow mGalleryDetailRow;
    private RelativeLayout mContainerGalleryRow;
    private String mPhoneNumberSelected;
    
    private PermissionCallBack mPermissionCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionGranted() {
            showDialogCall(mPropertyDetail.getPhones());
        }
        
        @Override
        public void onPermissionDenied() {
        
        }
    };
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolBar();
        initGalleryRow();
        mPropertyDetail = getIntent().getParcelableExtra(
            Constants.INTENT_EXTRA_PROPERTY_DETAIL);
        
        initRecyclerView();
        initRows();
        initButtonController();
        mPropertyDetailViewModel.getFullDetail(mPropertyDetail);
    }
    
    private void initButtonController() {
        Button buttonEmail = findViewById(R.id.email);
        buttonEmail.setOnClickListener(view -> {
            if (mPropertyDetail != null && !mPropertyDetail.getEmails().isEmpty()) {
            
            } else {
                Toast.makeText(this, R.string.message_email_list_is_empty, Toast.LENGTH_SHORT)
                    .show();
            }
        });
        
        Button buttonPhone = findViewById(R.id.call);
        buttonPhone.setOnClickListener(view -> {
            if (mPropertyDetail != null && !mPropertyDetail.getPhones().isEmpty()) {
                if (!PermissionHelper.isHasPermission(this, Permission.CALL_PERMISSION)) {
                    requireCallPermission();
                } else {
                    showDialogCall(mPropertyDetail.getPhones());
                }
            } else {
                Toast.makeText(this, R.string.message_phone_list_is_empty, Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }
    
    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    
    private void initGalleryRow() {
        mContainerGalleryRow = findViewById(R.id.gallery_row);
        
        mGalleryDetailRow = new GalleryDetailRow(this);
        mContainerGalleryRow.addView(mGalleryDetailRow);
    }
    
    private void initRows() {
        mDefaultRows = new ArrayList<>();
        
        VitalDetailRow vitalDetailRow = new VitalDetailRow();
        BedBathParkingRow bedBathParkingRow = new BedBathParkingRow();
        MapDetailRow mapDetailRow = new MapDetailRow();
        DirectionDetailRow directionDetailRow = new DirectionDetailRow();
        NoteDetailRow noteDetailRow = new NoteDetailRow();
        
        directionDetailRow.setDirectionRepository(mDirectionRepository);
        directionDetailRow.setDirectionsPreference(mDirectionsPreference);
        
        mDefaultRows.add(vitalDetailRow);
        mDefaultRows.add(bedBathParkingRow);
        
        mDefaultRows.add(mapDetailRow);
        mDefaultRows.add(directionDetailRow);
        
        mDefaultRows.add(noteDetailRow);
        
        mAdapter = new PropertyDetailRowAdapter(mDefaultRows);
        mAdapter.setRowControllerListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
    
    private void requireCallPermission() {
        if (PermissionHelper.shouldShowRequestPermissionRationale(this,
            PermissionHelper.Permission.READ_EXTERNAL_STORAGE)) {
            String message = this.getString(R.string.message_permission_call);
            String action = this.getString(R.string.txt_ok);
            SnackBarUtils.showSnackBar(findViewById(android.R.id.content), action, message,
                view -> PermissionHelper.askForPermission(this,
                    Permission.CALL_PERMISSION, mPermissionCallBack));
        } else {
            PermissionHelper
                .askForPermission(this,
                    Permission.CALL_PERMISSION,
                    mPermissionCallBack);
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unBindRow();
    }
    
    private void unBindRow() {
        for (int i = 0; i < this.mDefaultRows.size(); i++) {
            BaseRowViewHolder holder = getViewHolder((BaseRowDetail) this.mDefaultRows.get(i));
            if (holder != null) {
                holder.unbind();
            }
        }
    }
    
    private <ROW extends BaseRowDetail> BaseRowViewHolder<ROW> getViewHolder(
        ROW row) {
        Long itemId = mAdapter.getItemId(row);
        return this.mRecyclerView == null ? null : (BaseRowViewHolder) this.mRecyclerView
            .findViewHolderForItemId(itemId);
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    @Override
    protected BaseViewModel getViewModel(String tag) {
        return null;
    }
    
    @Override
    protected void processingTaskFromViewModel() {
        mPropertyDetailViewModel.response().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        if (resource.task.equals(TASK_GET_DETAIL_PROPERTY)) {
                            onFailureGetDetailProperty((Throwable) resource.data);
                            hideProgressBar();
                        }
                        break;
                    case SUCCESS:
                        if (resource.task.equals(TASK_GET_DETAIL_PROPERTY)) {
                            onSuccessGetDetailProperty((PropertyDetail) resource.data);
                            hideProgressBar();
                        }
                        break;
                    case LOADING:
                        showProgressBar();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property_detail, menu);
        if (mPropertyDetail != null) {
            menu.findItem(R.id.menu_item_favorite).setIcon(
                mPropertyDetail.isFavorite() ? R.drawable.ic_star_selected
                    : R.drawable.ic_star_un_selected);
        }
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_item_share:
                Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_favorite:
                mPropertyDetail.setFavorite(!mPropertyDetail.isFavorite());
                item.setIcon(mPropertyDetail.isFavorite() ? R.drawable.ic_star_selected
                    : R.drawable.ic_star_un_selected);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void addRow(BaseRowDetail row) {
        int defaultPosition = this.mDefaultRows.indexOf(row);
        int newPosition = 0;
        if (defaultPosition > -1) {
            for (int i = defaultPosition; i > -1; i--) {
                int prevRowIndex = this.mDefaultRows.indexOf(this.mDefaultRows.get(i));
                if (prevRowIndex > -1) {
                    newPosition = prevRowIndex + 1;
                    this.mDefaultRows.add(newPosition, row);
                    break;
                }
            }
        } else {
            this.mDefaultRows.add(row);
            newPosition = this.mDefaultRows.size() - 1;
        }
        this.mAdapter.notifyItemInserted(newPosition);
        mLayoutManager.scrollToPosition(newPosition);
    }
    
    @Override
    public void removeRow(BaseRowDetail row) {
        int rowIndex = this.mDefaultRows.indexOf(row);
        if (rowIndex > -1) {
            this.mDefaultRows.remove(rowIndex);
            this.mAdapter.notifyItemRemoved(rowIndex);
        }
    }
    
    @Override
    public void scrollToItem(StageRow stageRow) {
        int index = findIndexByStageRow(stageRow);
        mRecyclerView.scrollToPosition(index);
    }
    
    private int findIndexByStageRow(StageRow stageRow) {
        int index = 0;
        for (BaseRowDetail row : mDefaultRows) {
            index++;
            if (stageRow == row.getStageRow()) {
                break;
            }
        }
        return index;
    }
    
    @Override
    public void scrollToItem(int index) {
        mRecyclerView.scrollToPosition(index);
    }
    
    @Override
    public void scrollToBottom() {
        int size = mDefaultRows.size();
        mRecyclerView.scrollToPosition(size - 1);
    }
    
    @Override
    public void notifyInserted(StageRow stageRow) {
        mAdapter.notifyItemInserted(findIndexByStageRow(stageRow));
    }
    
    @Override
    public void notifyRemoved(StageRow stageRow) {
        mAdapter.notifyItemRemoved(findIndexByStageRow(stageRow));
    }
    
    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void scrollToTop() {
        mRecyclerView.scrollToPosition(0);
    }
    
    @Override
    public void notifyItemChanged(StageRow stageRow) {
        mAdapter.notifyItemChanged(findIndexByStageRow(stageRow));
    }
    
    @Override
    public void onSuccessGetDetailProperty(PropertyDetail propertyDetail) {
        mPropertyDetail = propertyDetail;
        mGalleryDetailRow.bind(mPropertyDetail);
        if (!TextUtils.isEmpty(mPropertyDetail.getDescription())) {
            DescriptionDetailRow descriptionDetailRow = new DescriptionDetailRow();
            mDefaultRows.add(2, descriptionDetailRow);
            mAdapter.getTypeToRow().put(descriptionDetailRow.getType(), descriptionDetailRow);
        }
        if (mPropertyDetail.getTags() != null && !mPropertyDetail.getTags().isEmpty()) {
            TagDetailRow tagDetailRow = new TagDetailRow();
            mDefaultRows.add(3, tagDetailRow);
            mAdapter.getTypeToRow().put(tagDetailRow.getType(), tagDetailRow);
        }
        
        for (BaseRowDetail row : mDefaultRows) {
            row.setData(propertyDetail);
        }
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onFailureGetDetailProperty(Throwable ex) {
        SnackBarUtils.showSnackBar(findViewById(android.R.id.content),
            getString(R.string.message_failure_get_full_detail));
    }
    
    @Override
    public void showProgressBar() {
    
    }
    
    @Override
    public void hideProgressBar() {
    
    }
    
    
    private void showDialogCall(List<MyPhone> myPhones) {
        SelectPhoneCallDialog dialog = SelectPhoneCallDialog.newInstance(myPhones);
        dialog.show(getSupportFragmentManager(), "TAG");
    }
}
