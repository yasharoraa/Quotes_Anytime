package com.Innovvapp.anytimequotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Innovvapp.anytimequotes.Activities.FullscreenPhotoActivity;
import com.Innovvapp.anytimequotes.Models.Quote;
import com.Innovvapp.anytimequotes.Utils.SquareImage;
import com.android.android.quotes.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yash Arora on 22-01-2018.
 */

public class QuotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private final List<Quote> mRecyclerViewItems;


    private final String TAG = QuotesAdapter.class.getSimpleName();


    private Context mContext;


    int quoteIdSend;

    public QuotesAdapter(Context context, List<Quote> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }








    public class QuotesItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_photo_photo)
        SquareImage photo;


        @BindView(R.id.item_quote_quote)
        TextView quoteText;


        @BindView(R.id.item_quote_author)
        TextView author;

        public QuotesItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_quote_layout)
        public void handleOnClick() {
            Log.d(TAG, "onclick");







            Intent intent = new Intent(mContext, FullscreenPhotoActivity.class);
            Bundle bun = new Bundle();



            String quoteSend = (quoteText.getText()).toString();
            String authorText = (author.getText()).toString();

            bun.putString("quote", quoteSend);

            bun.putString("author", authorText);

            bun.putInt("id", quoteIdSend);






            bun.putInt("image", getImageResourceId());

            intent.putExtras(bun);

            mContext.startActivity(intent);


        }


        public int getImageResourceId() {
            int ImageId = 0;
            Drawable drawable = photo.getDrawable();

            Drawable.ConstantState constantState = drawable.getConstantState();



            Drawable drawabletwo = mContext.getResources().getDrawable(R.drawable.imagetwo);

            Drawable.ConstantState constantState2 = drawabletwo.getConstantState();

            Drawable drawablefour= mContext.getResources().getDrawable(R.drawable.imagefour);

            Drawable.ConstantState constantState4 = drawablefour.getConstantState();


            Drawable drawablesix = mContext.getResources().getDrawable(R.drawable.imagesix);

            Drawable.ConstantState constantState6 = drawablesix.getConstantState();

            Drawable drawableseven = mContext.getResources().getDrawable(R.drawable.imageseven);

            Drawable.ConstantState constantState7 = drawableseven.getConstantState();

            Drawable drawableeight = mContext.getResources().getDrawable(R.drawable.imageeight);

            Drawable.ConstantState constantState8 = drawableeight.getConstantState();

            Drawable drawablenine= mContext.getResources().getDrawable(R.drawable.imagenine);

            Drawable.ConstantState constantState9 = drawablenine.getConstantState();





            if (constantState.equals(constantState2)) {

                ImageId = 102;


            }

            if (constantState.equals(constantState4)){
                ImageId = 104;
            }

            if (constantState.equals(constantState6)) {

                ImageId = 106;

            }
            if (constantState.equals(constantState7)) {

                ImageId = 107;


            }
            if (constantState.equals(constantState8)){

                ImageId = 108;
            }
            if (constantState.equals(constantState9)){
                ImageId = 109;
            }


            return ImageId;



            }

        }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {


                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_quote, viewGroup, false);


                return new QuotesItemViewHolder(itemView);
        }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



                QuotesItemViewHolder quotesItemViewHolder = (QuotesItemViewHolder) holder;


                Quote quote = (Quote) mRecyclerViewItems.get(position);


                quoteIdSend = quote.getId();

                quotesItemViewHolder.quoteText.setText(quote.getQuote());

                quotesItemViewHolder.author.setText(quote.getAuthor());
                int x = quote.getPhotoId();

                if (x == 0) {




                    final TypedArray imgs = mContext.getResources().obtainTypedArray(R.array.backgrounds);
                    final Random rand = new Random();
                    final int rndInt = rand.nextInt(imgs.length());
                    final int resID = imgs.getResourceId(rndInt, 0);

                    quotesItemViewHolder.photo.setImageResource(resID);


                } else {


                    switch (x) {

                        case 102:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imagetwo);

                            break;

                        case 104:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imagefour);

                            break;

                        case 106:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imagesix);

                            break;
                        case 107:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imageseven);

                            break;
                        case 108:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imageeight);

                            break;
                        case 109:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imagenine);

                            break;

                        default:
                            quotesItemViewHolder.photo.setImageResource(R.drawable.imageseven);
                    }


                }


    }




        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }


    }



