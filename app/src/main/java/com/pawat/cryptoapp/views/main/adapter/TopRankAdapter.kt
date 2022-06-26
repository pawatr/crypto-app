package com.pawat.cryptoapp.views.main.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.databinding.ListItemRegisterBinding
import com.pawat.cryptoapp.databinding.ListItemTopRankCoinBinding
import com.pawat.cryptoapp.views.main.adapter.listener.TopRankListener
import java.text.NumberFormat

class TopRankAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_COIN = 1
        const val VIEW_TYPE_REGISTER = 2
    }

    var topRankItems: List<Any> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var listener: TopRankListener? = null

    fun setListener(listener: TopRankListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COIN -> {
                TopRankViewHolder(parent.context,
                    ListItemTopRankCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                RegisterViewHolder(
                    ListItemRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            VIEW_TYPE_COIN -> {
                (holder as TopRankViewHolder).bindView(topRankItems[position] as Coin)
            }
            else -> {
                (holder as RegisterViewHolder).bindView()
            }
        }
    }

    override fun getItemCount() = topRankItems.size

    override fun getItemViewType(position: Int): Int {
        val item = topRankItems[position]
        return when (item.javaClass) {
            Coin::class.java -> VIEW_TYPE_COIN
            else -> {
                VIEW_TYPE_REGISTER
            }
        }
    }

    inner class TopRankViewHolder(private val context: Context, private val binding: ListItemTopRankCoinBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(coin: Coin) {
            binding.image.load(coin.image)
            binding.topCoinName.text = coin.name
            binding.topCoinSymbol.text = coin.symbol
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.maximumFractionDigits = 2
            val convert = numberFormat.format(coin.currentPrice)
            binding.topCoinPrice.text = convert.replace("฿","")
            if (coin.priceChangePercentageTwentyFourHour.toString().contains("-")){
                binding.topCoinChanged.setTextColor(context.getColor(R.color.red))
            } else {
                binding.topCoinChanged.setTextColor(context.getColor(R.color.green))
            }
            binding.topCoinChanged.text = "${String.format(itemView.context.getString(
                R.string.format_price), coin.priceChangePercentageTwentyFourHour)}%"
            itemView.setOnClickListener {
                listener?.onTopRankCoinClickListener(coin)
            }
        }
    }

    inner class RegisterViewHolder(private val binding: ListItemRegisterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView() {
            binding.root.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.INVITE_HOST_NAME))
                binding.root.context.startActivity(browserIntent)
            }
        }
    }
}