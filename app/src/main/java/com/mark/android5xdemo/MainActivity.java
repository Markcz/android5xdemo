package com.mark.android5xdemo;

import android.app.Dialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void btnOnClick(View view) {
        switch (view.getId()){
            case R.id.btn_toast:
                //costom toast
                showToast("hello, toast !");
                break;
            case R.id.btn_dialog:
                // custom dialog
                showDialog();
                break;


        }
    }

    Toast mToast;
    Dialog mDialog;
    TextView textView = null;
    void showDialog() {
        if (mDialog == null){
            mDialog = new Dialog(this,R.style.MyDialog);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view,null);

            Button btn_take_capture = (Button) dialogView.findViewById(R.id.btn_take_capture);
            Button btn_open_gallery = (Button) dialogView.findViewById(R.id.btn_open_gallery);
            Button btn_chooser = (Button) dialogView.findViewById(R.id.btn_double_chooser);
            Button btn_close_dialog = (Button) dialogView.findViewById(R.id.btn_close_dialog);

            btn_take_capture.setOnClickListener(this);
            btn_open_gallery.setOnClickListener(this);
            btn_chooser.setOnClickListener(this);
            btn_close_dialog.setOnClickListener(this);


            mDialog.setContentView(dialogView);
            ViewGroup.MarginLayoutParams params  = (ViewGroup.MarginLayoutParams) dialogView.getLayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels - 20;
            params.bottomMargin = 20;
            dialogView.setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        mDialog.show();
    }


    void showToast(String msg) {
        if (mToast == null){
            mToast = new Toast(this);
            View toastView = LayoutInflater.from(this).inflate(R.layout.toast_view,null);
            //toastView.animate().setInterpolator(new AccelerateInterpolator()).scaleX(0.5f).scaleY(0.5f).start();
            textView = (TextView) toastView.findViewById(R.id.tv_msg);
            mToast.setView(toastView);
            mToast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        textView.setText(msg);
        mToast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_take_capture:
                takeCapture();
                break;

            case R.id.btn_open_gallery:
                openGallery();
                break;

            case R.id.btn_double_chooser:
                Intent imageIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imageIntent.setType("image/*");
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooserIntent = Intent.createChooser(takeIntent,"Select Camera or Gallery ");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{imageIntent});

                startActivityForResult(chooserIntent,333);
                closeDialog();
                break;

            case R.id.btn_close_dialog:
                closeDialog();
                break;

        }
    }

    void closeDialog() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    void openGallery() {
        closeDialog();
        Intent imageIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent,11);
    }


    void takeCapture() {
        closeDialog();
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeIntent,22);
    }
}
