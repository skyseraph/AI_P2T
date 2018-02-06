package com.skyseraph.ai_p2t.ui.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.skyseraph.ai_p2t.R;
import com.skyseraph.ai_p2t.ui.beam.ColorName;
import com.skyseraph.ai_p2t.utils.TestData;
import com.skyseraph.ai_p2t.utils.UIUtils;

public class P2TStackAdapter extends StackAdapter<ColorName> {

    private static ItemJumpBtnClick itemJumpBtnClick;

    public P2TStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(ColorName data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeaderViewHolder) {
            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
            h.onBind(data, position);
        }
        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
            h.onBind(data, position);
        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.list_card_item_larger_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.list_card_item_with_no_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 6) {
//            return R.layout.list_card_item_larger_header;
//        } else if (position == 10) {
//            return R.layout.list_card_item_with_no_header;
//        } else {
        return R.layout.list_card_item;
//        }
    }

    public void setItemJumpBtnClick(ItemJumpBtnClick item) {
        itemJumpBtnClick = item;
    }

    public interface ItemJumpBtnClick {
        void onButtonClick(ColorName data);
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView mDes;
        Button mButton;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            mDes = (TextView) view.findViewById(R.id.tv_des);
            mButton = (Button) view.findViewById(R.id.button_view);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void onBind(final ColorName data, final int position) {
            if(data.getName().endsWith(TestData.NAME_DATAS[0])) {
                mLayout.setBackground(UIUtils.getContext().getResources().getDrawable(R.mipmap.mnist_0_9));
                mTextTitle.setTextColor(UIUtils.getContext().getResources().getColor(R.color.color_1));
            } else {
                mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
            }
            mTextTitle.setText(data.getName());
            mDes.setText(data.getDes());
            itemView.findViewById(R.id.button_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemJumpBtnClick != null) {
                        itemJumpBtnClick.onButtonClick(data);
                    }
                }
            });
        }
    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;

        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(ColorName data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(data.getName());
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false);
            }
        }

        public void onBind(ColorName data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data.getColor()), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(data.getName());

            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView) itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }
    }
}
