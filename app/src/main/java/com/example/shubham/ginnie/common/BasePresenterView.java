package com.example.shubham.ginnie.common;

import android.content.Context;

public interface BasePresenterView {

    void showMessage(int error);

    Context getContext();
}
