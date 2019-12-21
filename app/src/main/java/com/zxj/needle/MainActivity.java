package com.zxj.needle;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zxj.needle_annotation.NeedleAnnotation;
import com.zxj.needle_annotation.NeedleMapAnnotation;
import com.zxj.needle_annotation.NeedleParamsAnnotation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        View bt2 = findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        View bt3 = findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        View bt4 = findViewById(R.id.bt4);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                bt1("value1",1,2,3);
                break;
            case R.id.bt2:
                bt2("value1","value2","value3");
                break;
            case R.id.bt3:
                bt3();
                break;
            case R.id.bt4:
                bt4("value1",1,2,3);
                break;
        }
    }


    //第一种只有eventid，不带别的特殊的key
    @NeedleAnnotation(eventId = "click bt3")
    private void bt3() {
        Toast.makeText(this,"bt3",Toast.LENGTH_SHORT).show();
    }
    //第二种有eventid，也有入参作为打点的值的
    @NeedleAnnotation(eventId = "click bt2")
    private void bt2(@NeedleParamsAnnotation(key = "key1")String value1,
                     @NeedleParamsAnnotation(key = "key2")String value2,
                     @NeedleParamsAnnotation(key = "key3")String value3) {
        Toast.makeText(this,"bt2",Toast.LENGTH_SHORT).show();
    }
    //第三种有eventid，也有入参作为打点的值的，并且入参需要映射后才满足上报需求的
    @NeedleMapAnnotation(eventId = "click bt1",classez = {Bt4TrackDataMapping.class})
    private void bt1(@NeedleParamsAnnotation(key = "key1")String value1,
                     @NeedleParamsAnnotation(key = "key2")int i,
                     @NeedleParamsAnnotation(key = "key3")int i1,
                     @NeedleParamsAnnotation(key = "key4")int i2) {
        Toast.makeText(this,"bt1",Toast.LENGTH_SHORT).show();
    }

    //第四种有eventid，并且参数中有重复key
    @NeedleMapAnnotation(eventId = "click bt4",classez = {Bt4TrackDataMapping.class})
    private void bt4(@NeedleParamsAnnotation(key = "key1")String value1,
                     @NeedleParamsAnnotation(key = "key1")int i,
                     @NeedleParamsAnnotation(key = "key2")int i1,
                     @NeedleParamsAnnotation(key = "key2")int i2) {
        Toast.makeText(this,"bt4",Toast.LENGTH_SHORT).show();
    }
}
