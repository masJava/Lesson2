package com.example.lesson2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onEditText();
        bus();
    }

    @SuppressLint("CheckResult")
    private void bus() {
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);

        List<String> letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i"));

        Observable<String> observable = Observable.fromIterable(letters)
                .concatMap(item -> Observable.interval(3, TimeUnit.SECONDS)
                        .take(1)
                        .map(second -> item));

        List<String> letters2 = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));

        Observable<String> observable2 = Observable.fromIterable(letters2)
                .concatMap(item -> Observable.interval(2, TimeUnit.SECONDS)
                        .take(1)
                        .map(second -> item));

        PublishSubject<String> subject = PublishSubject.create();
        observable.subscribe(subject);
        observable2.subscribe(subject);
        subject.subscribe(textView1::setText);
        subject.onNext("Text");

        subject.subscribe(textView2::setText);

    }

    private void onEditText() {
        final TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}