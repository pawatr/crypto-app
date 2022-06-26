package com.pawat.cryptoapp.views.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.databinding.*
import com.pawat.cryptoapp.extensions.hideKeyboard
import com.pawat.cryptoapp.views.main.adapter.listener.CoinListListener
import java.text.NumberFormat

@Suppress("UNCHECKED_CAST")
class CoinListAdapter(private val topRankAdapter: TopRankAdapter): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_SEARCH = 0
        const val VIEW_TYPE_HEADER = 1
        const val VIEW_TYPE_COIN = 2
        const val VIEW_TYPE_INVITE = 3
        const val VIEW_TYPE_TOP_RANK = 4
    }

    var items: List<Any> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var listener: CoinListListener? = null

    fun setListener(listener: CoinListListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SEARCH -> {
                SearchViewHolder(parent.context,
                    ListItemSearchViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(parent.context,
                    ListItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            VIEW_TYPE_TOP_RANK -> {
                TopRankLayoutViewHolder(
                    ListItemHorizontalWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            VIEW_TYPE_COIN -> {
                CoinsViewHolder(parent.context,
                    ListItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                InviteFriendViewHolder(
                    ListItemInviteFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            VIEW_TYPE_SEARCH -> {
                (holder as SearchViewHolder).bindView()
            }
            VIEW_TYPE_HEADER -> {
                (holder as HeaderViewHolder).bindView()
            }
            VIEW_TYPE_TOP_RANK -> {
                (holder as TopRankLayoutViewHolder).bindView(items[position] as ArrayList<Coin>)
            }
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
        val item = items[position]
        return when (item.javaClass) {
            Coin::class.java -> VIEW_TYPE_COIN
            else -> {
                when (item) {
                    Constants.INVITE_FRIEND_VIEW -> VIEW_TYPE_INVITE
                    Constants.SEARCH_VIEW -> VIEW_TYPE_SEARCH
                    Constants.HEADER_VIEW -> VIEW_TYPE_HEADER
                    else -> VIEW_TYPE_TOP_RANK
                }
            }
        }
    }

    inner class SearchViewHolder(private val context: Context, private val binding: ListItemSearchViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(){
            binding.searchEdt.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val search = binding.searchEdt.text.toString()
                    if (search.trim().isNotEmpty()) {
                        listener?.onEditorActionListener(search)
                    }
                    context.hideKeyboard(binding.searchEdt)
                    return@OnEditorActionListener true
                }
                false
            })
            binding.searchEdt.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()){
                    listener?.onTextChangedToEmpty()
                    context.hideKeyboard(binding.searchEdt)
                    binding.searchEdt.clearFocus()
                    return@doOnTextChanged
                }
            }
        }
    }

    inner class HeaderViewHolder(private val context: Context, private val binding: ListItemHeaderBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bindView() {
            binding.header.text = context.getString(R.string.title_rank)
        }
    }

    inner class TopRankLayoutViewHolder(private val binding: ListItemHorizontalWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(topRankItems: ArrayList<Coin>) {
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayout.HORIZONTAL, false)
            binding.recyclerView.adapter = topRankAdapter
            topRankAdapter.topRankItems = topRankItems
        }
    }

    inner class CoinsViewHolder(private val context: Context, private val binding: ListItemCoinBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bindView(coin: Coin) {
            binding.coinItemImg.load(coin.image)
            binding.coinNameTv.text = coin.name
            binding.coinSymbolTv.text = coin.symbol
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.maximumFractionDigits = 2
            val convert = numberFormat.format(coin.currentPrice)
            binding.coinPriceTv.text = convert.replace("฿","")
            if (coin.priceChangePercentageTwentyFourHour.toString().contains("-")){
                binding.coinChangeTv.setTextColor(context.getColor(R.color.red))
            } else {
                binding.coinChangeTv.setTextColor(context.getColor(R.color.green))
            }
            binding.coinChangeTv.text = "${String.format(itemView.context.getString(
                R.string.format_price), coin.priceChangePercentageTwentyFourHour)}%"
            itemView.setOnClickListener {
                listener?.onCoinClickListener(coin)
            }
        }
    }

    inner class InviteFriendViewHolder(binding: ListItemInviteFriendBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(){
            itemView.setOnClickListener {
                listener?.onInviteClickListener()
            }
        }
    }
}