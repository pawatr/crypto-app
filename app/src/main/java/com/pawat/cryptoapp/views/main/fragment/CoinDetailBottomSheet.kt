package com.pawat.cryptoapp.views.main.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pawat.cryptoapp.data.model.CoinDetail
import com.pawat.cryptoapp.databinding.LayoutCoinBottomSheetBinding
import com.pawat.cryptoapp.extensions.numFormatter


class CoinDetailBottomSheet(var coinDetail: CoinDetail) : BottomSheetDialogFragment() {

    private val binding: LayoutCoinBottomSheetBinding by lazy { LayoutCoinBottomSheetBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coinDetailImg.load(coinDetail.image)
        binding.coinDetailNameTv.text = coinDetail.name
        binding.coinDetailSymbolTv.text = "(${coinDetail.symbol})"
        binding.coinDetailPriceTv.text = String.format("%,.2f", coinDetail.currentPrice)
        binding.coinDetailMarketCapTv.text = coinDetail.marketCap.numFormatter()
        binding.coinDetailDescriptionTv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(coinDetail.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(coinDetail.description)
        }
        binding.gotoTradeTv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(coinDetail.tradeUrl))
            startActivity(browserIntent)
        }
    }
}