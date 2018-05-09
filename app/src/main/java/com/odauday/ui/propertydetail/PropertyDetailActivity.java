package com.odauday.ui.propertydetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.odauday.R;
import com.odauday.config.AppConfig;
import com.odauday.model.Category;
import com.odauday.model.Image;
import com.odauday.model.PropertyDetail;
import com.odauday.model.Tag;
import com.odauday.ui.base.BaseActivity;
import com.odauday.ui.propertydetail.rowdetails.BaseRowDetail;
import com.odauday.ui.propertydetail.rowdetails.BaseRowViewHolder;
import com.odauday.ui.propertydetail.rowdetails.RowControllerListener;
import com.odauday.ui.propertydetail.rowdetails.bedbathpark.BedBathParkingRow;
import com.odauday.ui.propertydetail.rowdetails.description.DescriptionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.direction.DirectionDetailRow;
import com.odauday.ui.propertydetail.rowdetails.gallery.GalleryDetailRow;
import com.odauday.ui.propertydetail.rowdetails.map.MapDetailRow;
import com.odauday.ui.propertydetail.rowdetails.tag.TagDetailRow;
import com.odauday.ui.propertydetail.rowdetails.vital.VitalDetailRow;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 5/7/18.
 */
public class PropertyDetailActivity extends BaseActivity implements RowControllerListener {
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_full_detail;
    }
    
    RecyclerView mRecyclerView;
    List<BaseRowDetail<PropertyDetail, ? extends BaseRowViewHolder>> mDefaultRows;
    LinearLayoutManager mLayoutManager;
    
    PropertyDetailRowAdapter mAdapter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RelativeLayout relativeLayout = findViewById(R.id.gallery_row);
        
        GalleryDetailRow row = new GalleryDetailRow(this);
        relativeLayout.addView(row);
        
        PropertyDetail detail = new PropertyDetail();
        detail.setImages(new ArrayList<>());
        detail.getImages().add(new Image("1", "2"));
        detail.getImages().add(new Image("1", "2"));
        detail.getImages().add(new Image("1", "2"));
        detail.getImages().add(new Image("1", "2"));
        detail.getImages().add(new Image("1", "2"));
        detail.getImages().add(new Image("1", "2"));
        
        detail.setAddress(
            "Adddress dai nhat he mat troissssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        detail.setPrice(34567890234567890d);
        detail.setType(2);
        detail.setContactTime("Sang thu 3 ngay 18/2018");
        detail.setNumOfBedrooms(1);
        detail.setNumOfBathrooms(1);
        detail.setSize(1000);
        
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("0", "1"));
        categories.add(new Category("1", "1"));
        categories.add(new Category("2", "1"));
        categories.add(new Category("3", "1"));
        
        detail.setCategories(categories);
        
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < 100; i++) {
            builder.append("ABCDEFGHIKLMNOPMDSSS");
        }
        
        detail.setDescription(builder.toString());
        
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            tags.add(new Tag(String.valueOf(i), "name"));
        }
        detail.setTags(tags);
        
        detail.setLocation(AppConfig.DEFAULT_GEO_LOCATION);
        
        row.bind(detail);
        
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        
        mDefaultRows = new ArrayList<>();
        
        VitalDetailRow vitalDetailRow = new VitalDetailRow();
        BedBathParkingRow bedBathParkingRow = new BedBathParkingRow();
        DescriptionDetailRow descriptionDetailRow = new DescriptionDetailRow();
        TagDetailRow tagDetailRow = new TagDetailRow();
        MapDetailRow mapDetailRow = new MapDetailRow();
        DirectionDetailRow directionDetailRow = new DirectionDetailRow();
        
        vitalDetailRow.setData(detail);
        bedBathParkingRow.setData(detail);
        descriptionDetailRow.setData(detail);
        tagDetailRow.setData(detail);
        mapDetailRow.setData(detail);
        directionDetailRow.setData(detail);
        
        mDefaultRows.add(vitalDetailRow);
        mDefaultRows.add(bedBathParkingRow);
        mDefaultRows.add(descriptionDetailRow);
        mDefaultRows.add(tagDetailRow);
        mDefaultRows.add(mapDetailRow);
        mDefaultRows.add(directionDetailRow);
        
        mAdapter = new PropertyDetailRowAdapter(mDefaultRows);
        mAdapter.setRowControllerListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property_detail, menu);
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
                Toast.makeText(this, "FAVORITE", Toast.LENGTH_SHORT).show();
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
}
