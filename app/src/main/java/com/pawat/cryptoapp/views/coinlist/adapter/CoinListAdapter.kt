package com.pawat.cryptoapp.views.coinlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.views.coinlist.adapter.listener.CoinListAdapterListener
import kotlinx.android.synthetic.main.coin_list_item.view.*
import java.text.NumberFormat

class CoinListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_COIN = 1
        const val VIEW_TYPE_INVITE = 2
    }

    var items: List<Any> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var listener: CoinListAdapterListener? = null

    fun setListener(listener: CoinListAdapterListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COIN -> {
                CoinsViewHolder(
                    LayoutInflater.from(parent.context)
                    .inflate(R.layout.coin_list_item, parent, false))
            }
            else -> {
                InviteFriendViewHolder(
                    LayoutInflater.from(parent.context)
                    .inflate(R.layout.invite_friend_list_item, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            VIEW_TYPE_COIN -> {
                (holder as CoinsViewHolder).bindView(items[position] as Coin)
            }
            else -> {
                (holder as InviteFriendViewHolder).bindView()
            }
        }
        if (position == items.size - 1){
            listener?.onScrollToBottomListener()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].javaClass) {
            Coin::class.java -> VIEW_TYPE_COIN
            else -> VIEW_TYPE_INVITE
        }
    }

    inner class CoinsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(coin: Coin) {
            itemView.coinItemImg.load(coin.image)
            itemView.coinNameTv.text = coin.name
            itemView.coinSymbolTv.text = coin.symbol
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.maximumFractionDigits = 2
            val convert = numberFormat.format(coin.currentPrice)
            itemView.coinPriceTv.text = convert.replace("฿","")
            if (coin.priceChangePercentageTwentyFourHour.toString().contains("-")){
                itemView.coinChangeTv.setTextColor(itemView.context.getColor(R.color.red))
            } else {
                itemView.coinChangeTv.setTextColor(itemView.context.getColor(R.color.green))
            }
            itemView.coinChangeTv.text = "${String.format(itemView.context.getString(
                R.string.format_price), coin.priceChangePercentageTwentyFourHour)}%"
            itemView.setOnClickListener {
                listener?.onCoinClickListener(coin)
            }
        }
    }

    inner class InviteFriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(){
            itemView.setOnClickListener {
                listener?.onInviteClickListener()
            }
        }
    }
}