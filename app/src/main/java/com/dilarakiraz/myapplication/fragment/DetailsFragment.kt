package com.dilarakiraz.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dilarakiraz.myapplication.R
import com.dilarakiraz.myapplication.databinding.FragmentDetailsBinding
import com.dilarakiraz.myapplication.models.CryptoCurrency


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    private val args : DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentDetailsBinding.inflate(inflater, container, false)

        val data:CryptoCurrency = args.data!!

        setupDetails(data)
//        loadChart(data)
        setButtonOnClick(data)

        return binding.root
    }



    private fun setButtonOnClick(data: CryptoCurrency) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMinute = binding.button5

        val clickListener = View.OnClickListener {
            val interval = when (it.id) {
                fifteenMinute.id -> "15"
                oneHour.id -> "1H"
                fourHour.id -> "4H"
                oneDay.id -> "D"
                oneWeek.id -> "W"
                oneMonth.id -> "M"
                else -> "15" // Varsayılan olarak 15 dakika
            }
            loadChart(data, interval)
            disableButton(oneDay, oneMonth, oneWeek, fourHour, oneHour, fifteenMinute, it as AppCompatButton)
            it.setBackgroundResource(R.drawable.active_button)
        }
        fifteenMinute.setOnClickListener (clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)

    }
    private fun loadChart(data: CryptoCurrency, interval: String) {
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        binding.detaillChartWebView.loadUrl(
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=${data.symbol}USD&interval=1D&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        )
    }

//    private fun loadChartData(interval: String, data: CryptoCurrency) {
//        binding.detaillChartWebView.settings.javaScriptEnabled = true
//        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//
//        binding.detaillChartWebView.loadUrl(
//            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=${data.symbol}USD&interval=$interval&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
//        )
//    }

    private fun disableButton(vararg buttons: AppCompatButton) {
        for (button in buttons) {
            button.background = null
        }
    }


//    private fun loadChart(item: CryptoCurrency) {
//        binding.detaillChartWebView.settings.javaScriptEnabled = true
//        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
//
//        binding.detaillChartWebView.loadUrl(
//
//            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol" + item.symbol
//                .toString() + "USD&interval=&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
//        )
//    }

    private fun setupDetails(data:CryptoCurrency) {
        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext()).load(
            "https://s2.coinmarketcap.com/static/img/coins/64x64/" + data.id + ".png"
        ).into(binding.detailImageView)


        binding.detailPriceTextView.text= "${String.format("$%.4f",data.quotes[0].price)}"

        if(data.quotes!![0].percentChange24h > 0){
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text = "+ ${String.format("%.02f",data.quotes[0].percentChange24h)} %"
        }else{
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.setTextColor(requireContext().resources.getColor(R.color.red))
            binding.detailChangeTextView.text = "${String.format("%.02f",data.quotes[0].percentChange24h)} %"
        }
    }
}