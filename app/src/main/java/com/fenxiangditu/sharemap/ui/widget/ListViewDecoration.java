/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fenxiangditu.sharemap.ui.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fenxiangditu.sharemap.application.AppContext;

import sharemap.R;

/** for swipe listView Created by Yan Zhenjie on 2016/7/27. */
public class ListViewDecoration extends RecyclerView.ItemDecoration {

  private Drawable mDrawable;
  private int mLeft = 0; //距离左边距离
  private int mRight = 0; //距离右边距离

  public ListViewDecoration() {
    mDrawable = ContextCompat.getDrawable(AppContext.getContext(), R.drawable.divider_recycler);
  }

  @Override
  public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    final int left = parent.getPaddingLeft() + this.mLeft;
    final int right = parent.getWidth() - parent.getPaddingRight();

    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount - 1; i++) {
      final View child = parent.getChildAt(i);
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      // 以下计算主要用来确定绘制的位置
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = top + mDrawable.getIntrinsicHeight();
      mDrawable.setBounds(left, top, right, bottom);
      mDrawable.draw(c);
    }
  }

  @Override
  public void getItemOffsets(
          Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
  }

  public void setLeft(int left) {
    this.mLeft = left;
  }

  public void setRight(int right) {
    this.mRight = right;
  }

  public int getLeft() {
    return mLeft;
  }

  public int getRight() {
    return mRight;
  }
}
