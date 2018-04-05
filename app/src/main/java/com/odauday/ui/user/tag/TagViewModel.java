package com.odauday.ui.user.tag;

import android.util.Log;
import com.odauday.data.TagRepository;
import com.odauday.model.Tag;
import com.odauday.viewmodel.BaseViewModel;
import com.odauday.viewmodel.model.Resource;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

/**
 * Created by kunsubin on 4/1/2018.
 */

public class TagViewModel extends BaseViewModel {
    
    
    TagRepository mTagRepository;
    
    @Inject
    public TagViewModel(TagRepository tagRepository) {
        mTagRepository = tagRepository;
    }
    public void getAllTag(){
        Log.d("Lang thang","Khong nha");
        Disposable disposable=mTagRepository.getAllTag()
                  .doOnSubscribe(onSubscribe -> {
                      Log.d("Lang thang","loading");
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(success -> {
                      Log.d("Lang thang","thanh cong");
                      Log.d("Lang thang",success.toString());
                      response.setValue(Resource.success(success));
                  }, error -> {
                      Log.d("Lang thang","that bai");
                      response.setValue(Resource.error(error));
                  });
    
        mCompositeDisposable.add(disposable);
    }
    public void createTag(Tag tag){
        Disposable disposable=mTagRepository.createTag(tag)
                  .doOnSubscribe(onSubscribe -> {
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(success -> {
                      response.setValue(Resource.success(success));
                  }, error -> {
                      response.setValue(Resource.error(error));
                  });
    
        mCompositeDisposable.add(disposable);
    }
    public void updateTag(Tag tag){
        Disposable disposable=mTagRepository.updateTag(tag)
                  .doOnSubscribe(onSubscribe -> {
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(success -> {
                      response.setValue(Resource.success(success));
                  }, error -> {
                      response.setValue(Resource.error(error));
                  });
    
        mCompositeDisposable.add(disposable);
    }
    public void deleteTag(String tag_id){
        Disposable disposable=mTagRepository.deleteTag(tag_id)
                  .doOnSubscribe(onSubscribe -> {
                      response.setValue(Resource.loading(null));
                  })
                  .subscribe(success -> {
                      response.setValue(Resource.success(success));
                  }, error -> {
                      response.setValue(Resource.error(error));
                  });
    
        mCompositeDisposable.add(disposable);
    }
    
}
