package dy.utils.libphotopicker.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dy.utils.libphotopicker.R;
import dy.utils.libphotopicker.utils.ImageUtil;

public class PhotoPickerActivity
        extends AppCompatActivity {

    public static final String TAG = PhotoPickerActivity.class.getName();

    private Context mCxt;


    /** 图片选择模式，int类型 */
    public static final String IS_OFFER = "is_offer";
    /** 图片选择模式，int类型 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** 单选 */
    public static final int MODE_SINGLE = 0;
    /** 多选 */
    public static final int MODE_MULTI = 1;
    /** 最大图片选择次数，int类型 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** 默认最大照片数量 */
    public static final int DEFAULT_MAX_TOTAL= 9;
    /** 是否显示相机，boolean类型 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** 默认选择的数据集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    /** 筛选照片配置信息 */
    public static final String EXTRA_IMAGE_CONFIG = "image_config";
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "select_result";

    // 结果数据
    private ArrayList<String> resultList = new ArrayList<>();

    // 不同loader定义
    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;

//    private MenuItem menuDoneItem;
    private GridView mGridView;
    private ImageView iv_back;
    private TextView tv_done;

    // 最大照片数量
    private ImageCaptureManager captureManager;
    private int mDesireImageCount;
    private boolean isOffer = false;
    private ImageConfig imageConfig; // 照片配置

    private ImageGridAdapter mImageAdapter;

    private boolean hasFolderGened = false;
    private boolean mIsShowCamera = false;

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.push_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);

        initViews();

        // 照片属性
        imageConfig = getIntent().getParcelableExtra(EXTRA_IMAGE_CONFIG);

        // 首次加载所有图片
        getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);

        // 选择图片数量
        mDesireImageCount = getIntent().getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_MAX_TOTAL);

        // 图片选择模式
        final int mode = getIntent().getIntExtra(EXTRA_SELECT_MODE, MODE_SINGLE);

        isOffer = getIntent().getBooleanExtra(IS_OFFER, false);

        // 默认选择
        if(mode == MODE_MULTI) {
            ArrayList<String> tmp = getIntent().getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
            if(tmp != null && tmp.size() > 0) {
                resultList.addAll(tmp);
            }
        }

        // 是否显示照相机
        mIsShowCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, false);
        mImageAdapter = new ImageGridAdapter(mCxt, mIsShowCamera, getItemImageWidth());
        // 是否显示选择指示器
        mImageAdapter.showSelectIndicator(mode == MODE_MULTI);
        mGridView.setAdapter(mImageAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mImageAdapter.isShowCamera()) {
                    // 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
                    if (i == 0) {
                        if(mode == MODE_MULTI){
                            // 判断选择数量问题
                            if(mDesireImageCount == resultList.size()-1){
                                Toast.makeText(mCxt, R.string.msg_amount_limit, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        showCameraAction();
                    } else {
                        // 正常操作
                        Image image = (Image) adapterView.getAdapter().getItem(i);
                        selectImageFromGrid(image, mode);
                    }
                } else {
                    // 正常操作
                    Image image = (Image) adapterView.getAdapter().getItem(i);
                    selectImageFromGrid(image, mode);
                }
            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                complete();
            }
        });
        refreshActionStatus();
    }

    private void initViews(){
        mCxt = this;
        captureManager = new ImageCaptureManager(mCxt);
        // ActionBar Setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.pickerToolbar);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_done = (TextView) findViewById(R.id.tv_done);

        mGridView = (GridView) findViewById(R.id.grid);
        mGridView.setNumColumns(getNumColnums());

    }


    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        refreshActionStatus();
    }

    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        refreshActionStatus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                // 相机拍照完成后，返回图片路径
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        resultList.add(captureManager.getCurrentPhotoPath());
                    }
                    complete();
                    break;
                default:break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        // 重置列数
        mGridView.setNumColumns(getNumColnums());
        // 重置Item宽度
        mImageAdapter.setItemSize(getItemImageWidth());

        super.onConfigurationChanged(newConfig);
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        try {
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            Toast.makeText(mCxt, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 选择图片操作
     * @param image
     */
    private void selectImageFromGrid(Image image, int mode) {
        if(image != null) {
            try {

                int size = ImageUtil.getFileSizes(image.path);
                if (size <= 0){
                    Toast.makeText(this, R.string.image_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (size > 1048576 * 16){
                    Toast.makeText(this, R.string.image_big, Toast.LENGTH_SHORT).show();
                    return;
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 多选模式
            if(mode == MODE_MULTI) {
                if (resultList.contains(image.path)) {
                    resultList.remove(image.path);
                    onImageUnselected(image.path);
                } else {
                    //供稿时需要判断总size
                    if(mDesireImageCount == resultList.size()){
                        Toast.makeText(mCxt, R.string.msg_amount_limit, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 判断选择数量问题
                    if (isOffer){
                        if (ImageUtil.getListTotalSizeExceed(resultList, image.path)){
                            Toast.makeText(this, getString(R.string.photo_pick_total_size_not_cover_30M), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    resultList.add(image.path);
                    onImageSelected(image.path);
                }
                mImageAdapter.select(image);
            }else if(mode == MODE_SINGLE){
                // 单选模式
                onSingleImageSelected(image.path);
            }
        }
    }

    /**
     * 刷新操作按钮状态
     */
    private void refreshActionStatus(){
        if(resultList.contains("add")){
            resultList.remove("add");
        }
        String text = getString(R.string.done_with_count, resultList.size(), mDesireImageCount);
        tv_done.setText(text);
        boolean hasSelected = resultList.size() > 0;
        if (hasSelected){
            tv_done.setVisibility(View.VISIBLE);
        } else {
            tv_done.setVisibility(View.GONE);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            // 根据图片设置参数新增验证条件
            StringBuilder selectionArgs = new StringBuilder();

            if(imageConfig != null){
                if(imageConfig.minWidth != 0){
                    selectionArgs.append(MediaStore.Images.Media.WIDTH + " >= ").append(imageConfig.minWidth);
                }

                if(imageConfig.minHeight != 0){
                    selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
                    selectionArgs.append(MediaStore.Images.Media.HEIGHT + " >= ").append(imageConfig.minHeight);
                }

                if(imageConfig.minSize != 0f){
                    selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
                    selectionArgs.append(MediaStore.Images.Media.SIZE + " >= ").append(imageConfig.minSize);
                }

                if(imageConfig.mimeType != null){
                    selectionArgs.append(" and (");
                    for(int i = 0, len = imageConfig.mimeType.length; i < len; i++){
                        if(i != 0){
                            selectionArgs.append(" or ");
                        }
                        selectionArgs.append(MediaStore.Images.Media.MIME_TYPE + " = '").append(imageConfig.mimeType[i]).append("'");
                    }
                    selectionArgs.append(")");
                }
            }

            if(id == LOADER_ALL) {
                CursorLoader cursorLoader = new CursorLoader(mCxt,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        selectionArgs.toString(), null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }else if(id == LOADER_CATEGORY){
                String selectionStr = selectionArgs.toString();
                if(!"".equals(selectionStr)){
                    selectionStr += " and" + selectionStr;
                }
                CursorLoader cursorLoader = new CursorLoader(mCxt,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'" + selectionStr, null,
                        IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }

            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                List<Image> images = new ArrayList<>();
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do{
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                        Image image = new Image(path, name, dateTime);
                        images.add(image);

                    }while(data.moveToNext());

                    mImageAdapter.setData(images);

                    // 设定默认选择
                    if(resultList != null && resultList.size()>0){
                        mImageAdapter.setDefaultSelected(resultList);
                    }

                    hasFolderGened = true;

                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 判断文件是否为图片文件(GIF,PNG,JPG)
     * @param srcFileName
     * @return
     */
/*    public boolean isGifImage(String srcFileName) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFileName);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }*/

    /**
     * 获取GridView Item宽度
     * @return
     */
    private int getItemImageWidth(){
        int cols = getNumColnums();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
        return (screenWidth - columnSpace * (cols-1)) / cols;
    }

    /**
     * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列
     * @return
     */
    private int getNumColnums(){
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        return cols < 3 ? 3 : cols;
    }


    // 返回已选择的图片数据
    private void complete(){
        Intent data = new Intent();
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        captureManager.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
