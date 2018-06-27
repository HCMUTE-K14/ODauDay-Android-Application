package com.odauday.ui.propertydetail;

import static com.odauday.config.Constants.Task.TASK_GET_DETAIL_PROPERTY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.Constants;
import com.odauday.data.DirectionRepository;
import com.odauday.data.FavoriteRepository;
import com.odauday.data.NoteRepository;
import com.odauday.data.SimilarPropertyRepository;
import com.odauday.data.local.cache.DirectionsPreferenceHelper;
import com.odauday.model.MyPhone;
import com.odauday.model.PropertyDetail;
import com.odauday.ui.base.BaseMVVMActivity;
import com.odauday.ui.propertydetail.common.OnChangeNoteEvent;
import com.odauday.ui.propertydetail.common.SelectPhoneCallDialog;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.RowControllerListener;
import com.odauday.ui.propertydetail.rowdetails.bedbathpark.BedBathParkingRow;
import com.odauday.ui.propertydetail.rowdetails.description.DescriptionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.direction.DirectionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.enquiry.EnquiryDetailRow;
import com.odauday.ui.propertydetail.rowdetails.gallery.GalleryDetailRow;
import com.odauday.ui.propertydetail.rowdetails.map.MapDetailRow;
import com.odauday.ui.propertydetail.rowdetails.note.NoteDetailRow;
import com.odauday.ui.propertydetail.rowdetails.similar.SimilarPropertyRow;
import com.odauday.ui.propertydetail.rowdetails.tag.TagDetailRow;
import com.odauday.ui.propertydetail.rowdetails.vital.VitalDetailRow;
import com.odauday.utils.SnackBarUtils;
import com.odauday.utils.TextUtils;
import com.odauday.utils.ViewUtils;
import com.odauday.viewmodel.BaseViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/**
 * Created by infamouSs on 5/7/18.
 */
@SuppressLint({"MissingPermission", "CheckResult"})
public class PropertyDetailActivity extends BaseMVVMActivity implements RowControllerListener,
                                                                        PropertyDetailContract {
    
    
    public static final String TAG = PropertyDetailActivity.class.getSimpleName();
    
    private RecyclerView mRecyclerView;
    private List<BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> mDefaultRows;
    private LinearLayoutManager mLayoutManager;
    
    private PropertyDetailRowAdapter mAdapter;
    
    @Inject
    DirectionsPreferenceHelper mDirectionsPreferenceHelper;
    
    @Inject
    DirectionRepository mDirectionRepository;
    
    @Inject
    NoteRepository mNoteRepository;
    
    @Inject
    PropertyDetailViewModel mPropertyDetailViewModel;
    
    @Inject
    FavoriteRepository mFavoriteRepository;
    
    @Inject
    SimilarPropertyRepository mSimilarPropertyRepository;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_full_detail;
    }
    
    private PropertyDetail mPropertyDetail;
    private GalleryDetailRow mGalleryDetailRow;
    private RelativeLayout mContainerGalleryRow;
    private String mPhoneNumberSelected;
    private AppBarLayout mAppBarLayout;
    private ProgressBar mProgressBar;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mProgressBar = findViewById(R.id.progress);
        initToolBar();
        initGalleryRow();
        mPropertyDetail = getIntent().getParcelableExtra(
            Constants.INTENT_EXTRA_PROPERTY_DETAIL);
        
        initRecyclerView();
        initRows();
        initButtonController();
        mPropertyDetailViewModel.getFullDetail(mPropertyDetail);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeNote(OnChangeNoteEvent noteEvent) {
        mPropertyDetail.setFavorite(true);
        for (BaseRowDetail row : mDefaultRows) {
            row.setData(mPropertyDetail);
        }
        invalidateOptionsMenu();
    }
    
    
    private void initButtonController() {
        Button buttonEmail = findViewById(R.id.email);
        buttonEmail.setOnClickListener(view -> {
            new Handler().postDelayed(this::collapseAppBar, 50);
            
            scrollToItem(StageRow.ENQUIRY_ROW);
        });
        
        Button buttonPhone = findViewById(R.id.call);
        buttonPhone.setOnClickListener(view -> {
            if (mPropertyDetail != null && !mPropertyDetail.getPhones().isEmpty()) {
                showDialogCall(mPropertyDetail.getPhones());
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
        
        mAppBarLayout = findViewById(R.id.appbar);
        
    }
    
    private void collapseAppBar() {
        mAppBarLayout.setExpanded(false, true);
    }
    
    private void expandAppBar() {
        mAppBarLayout.setExpanded(true, false);
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
        EnquiryDetailRow enquiryDetailRow = new EnquiryDetailRow();
        SimilarPropertyRow similarPropertyRow = new SimilarPropertyRow();
        
        directionDetailRow.setDirectionRepository(mDirectionRepository);
        directionDetailRow.setDirectionsPreferenceHelper(mDirectionsPreferenceHelper);
        
        noteDetailRow.setNoteRepository(mNoteRepository);
        noteDetailRow.setFavoriteRepository(mFavoriteRepository);
        similarPropertyRow.setSimilarPropertyRepository(mSimilarPropertyRepository);
        
        mDefaultRows.add(vitalDetailRow);
        mDefaultRows.add(bedBathParkingRow);
        
        mDefaultRows.add(mapDetailRow);
        mDefaultRows.add(directionDetailRow);
        
        mDefaultRows.add(noteDetailRow);
        mDefaultRows.add(enquiryDetailRow);
        
        mDefaultRows.add(similarPropertyRow);
        
        mAdapter = new PropertyDetailRowAdapter(mDefaultRows);
        mAdapter.setRowControllerListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
    }
    
    @Override
    public void finish() {
        overridePendingTransition(R.anim.fade_out, 0);
        super.finish();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_favorite);
        menuItem.setIcon(mPropertyDetail.isFavorite() ? R.drawable.ic_star_selected
            : R.drawable.ic_star_un_selected);
        return super.onPrepareOptionsMenu(menu);
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
                shareProperty();
                return true;
            case R.id.menu_item_favorite:
                if (!mPropertyDetail.isFavorite()) {
                    checkFavorite();
                } else {
                    unCheckFavorite();
                }
                mPropertyDetail.setFavorite(!mPropertyDetail.isFavorite());
                item.setIcon(mPropertyDetail.isFavorite() ? R.drawable.ic_star_selected
                    : R.drawable.ic_star_un_selected);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void checkFavorite() {
        mFavoriteRepository
            .checkFavorite(mPropertyDetail.getId())
            .subscribe(success -> {
            
            }, throwable -> {
                Timber.d(throwable.getMessage());
            });
    }
    
    private void unCheckFavorite() {
        mFavoriteRepository
            .unCheckFavorite(mPropertyDetail.getId())
            .subscribe(success -> {
            
            }, throwable -> {
                Timber.d(throwable.getMessage());
            });
    }
    
    @Override
    public void addRow(BaseRowDetail row) {
        ViewUtils.delay(() -> {
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
        }, 100);
        
    }
    
    @Override
    public void removeRow(BaseRowDetail row) {
        int rowIndex = this.mDefaultRows.indexOf(row);
        ViewUtils.delay(() -> {
            if (rowIndex > -1) {
                this.mDefaultRows.remove(rowIndex);
                this.mAdapter.notifyItemRemoved(rowIndex);
            }
        }, 100);
        
    }
    
    @Override
    public void scrollToItem(StageRow stageRow) {
        int index = findIndexByStageRow(stageRow);
        mRecyclerView.smoothScrollToPosition(index);
    }
    
    private int findIndexByStageRow(StageRow stageRow) {
        int index = 0;
        for (BaseRowDetail row : mDefaultRows) {
            if (stageRow == row.getStageRow()) {
                break;
            }
            index++;
        }
        return index;
    }
    
    @Override
    public void scrollToItem(int index) {
        mRecyclerView.smoothScrollToPosition(index);
    }
    
    @Override
    public void scrollToBottom() {
        int size = mDefaultRows.size();
        mRecyclerView.smoothScrollToPosition(size - 1);
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
        ViewUtils.delay(() -> {
            mRecyclerView.smoothScrollToPosition(0);
        }, 20);
    }
    
    @Override
    public void notifyItemChanged(StageRow stageRow) {
        mAdapter.notifyItemChanged(findIndexByStageRow(stageRow));
    }
    
    @Override
    public void onSuccessGetDetailProperty(PropertyDetail propertyDetail) {
        if (mPropertyDetail != null && mPropertyDetail.equals(propertyDetail)) {
            return;
        }
        mPropertyDetail = propertyDetail;
        if (!TextUtils.isEmpty(mPropertyDetail.getDescription())) {
            DescriptionDetailRow descriptionDetailRow = new DescriptionDetailRow();
            mDefaultRows.add(StageRow.DESCRIPTION_ROW.getPos(), descriptionDetailRow);
            mAdapter.getTypeToRow().put(descriptionDetailRow.getType(), descriptionDetailRow);
        }
        if (mPropertyDetail.getTags() != null && !mPropertyDetail.getTags().isEmpty()) {
            TagDetailRow tagDetailRow = new TagDetailRow();
            mDefaultRows.add(StageRow.TAGS_ROW.getPos(), tagDetailRow);
            mAdapter.getTypeToRow().put(tagDetailRow.getType(), tagDetailRow);
        }
        
        for (BaseRowDetail row : mDefaultRows) {
            row.setData(propertyDetail);
        }
        mAdapter.notifyDataSetChanged();
        if (mPropertyDetail.getImages() != null && !mPropertyDetail.getImages().isEmpty()) {
            expandAppBar();
            scrollToTop();
        } else {
            scrollToTop();
        }
        mGalleryDetailRow.bind(mPropertyDetail);
    }
    
    @Override
    public void onFailureGetDetailProperty(Throwable ex) {
        SnackBarUtils.showSnackBar(findViewById(android.R.id.content),
            getString(R.string.message_failure_get_full_detail));
    }
    
    @Override
    public void showProgressBar() {
        ViewUtils.showHideView(mProgressBar, true);
    }
    
    @Override
    public void hideProgressBar() {
        ViewUtils.showHideView(mProgressBar, false);
    }
    
    
    private void showDialogCall(List<MyPhone> myPhones) {
        SelectPhoneCallDialog dialog = SelectPhoneCallDialog.newInstance(myPhones);
        dialog.show(getSupportFragmentManager(), "TAG");
    }
    
    private void shareProperty() {
        Intent intent = Intent
            .createChooser(createShareIntent(), getString(R.string.txt_share_property_label));
        startActivity(intent);
    }
    
    private Intent createShareIntent() {
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("text/plain");
        shareIntent.putExtra("android.intent.extra.TEXT",
            (!TextUtils.isEmpty(mPropertyDetail.getAddress()) ?
                TextUtils.formatAddress(mPropertyDetail.getAddress()) + "\\n"
                : "") +
            getString(R.string.txt_share_text));
        shareIntent.putExtra("android.intent.extra.SUBJECT",
            getString(R.string.txt_share_subject, mPropertyDetail.getAddress()));
        
        return shareIntent;
    }
}
