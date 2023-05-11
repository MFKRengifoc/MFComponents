package com.manoffocus.mfcomponentsnav.components.mfimageview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.manoffocus.mfcomponentsnav.R
import com.manoffocus.mfcomponentsnav.databinding.MfImageViewBinding


class MFImageView : ConstraintLayout {
    private lateinit var binding : MfImageViewBinding
    private var mfImageDraw : Drawable? = null
    private var mfImageUrl: String = ""
    private var mfDefaultContentDes : String? = null
    private var mfImageSize : Float? = null
    protected open var placeholder: Drawable? = null
    protected open var errorImage: Drawable? = null

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }
    private fun initialize(attrs: AttributeSet?) {
        loadAttrs(attrs)
        initView()
    }
    private fun loadAttrs(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MFImageView,
            ZERO,
            ZERO
        ).apply {
            contentDescription?.let { content ->
                mfDefaultContentDes = content.toString()
            } ?: kotlin.run {
                mfDefaultContentDes = EMPTY_STRING
            }
            mfImageSize = getDimension(R.styleable.MFImageView_mf_image_size, -1F)
            try {
                mfImageDraw = getDrawable(R.styleable.MFImageView_mf_image_draw) as Drawable
            } catch (e: Exception) {
                e.localizedMessage
            }
        }
    }
    private fun initView() {
        binding = MfImageViewBinding.inflate(LayoutInflater.from(context), this)
        setDrawable(mfImageDraw)
        setImageUrl("", null)
        isClickable = true
        setImageSize(mfImageSize)
    }
    private fun setDrawable(drawableImage: Drawable?) {
        drawableImage?.let { image ->
            binding.mfImageView.setImageDrawable(image)
        }
    }
    fun externalSetDrawable(drawableImage: Drawable){
        setDrawable(drawableImage)
    }

    fun setImageUrl(imageUrl: String, bitmapTransformation: BitmapTransformation?) {
        if (this.mfImageUrl != imageUrl) {
            this.mfImageUrl = imageUrl
            val builder: RequestBuilder<Drawable> = Glide.with(context).load(imageUrl)
                .error(errorImage ?: getDefaultErrorDrawable())
                .placeholder(placeholder ?: getDefaultPlaceholderDrawable())
            bitmapTransformation?.let {
                builder.apply(RequestOptions().centerInside().transform(CenterInside(), bitmapTransformation))
            }
            builder.into(binding.mfImageView)
        }
    }
    fun setImgUrl(imgUrl: String){
        setImageUrl(imgUrl, null)
    }
    fun setImageSize(size: Float?){
        if (size != -1F){
            val lp = binding.mfImageView.layoutParams
            lp.width = spToPx(size!!)
            lp.height = spToPx(size!!)
            binding.mfImageView.layoutParams = lp
        }
    }

    private fun getDefaultErrorDrawable() =
        ResourcesCompat.getDrawable(resources, R.drawable.mf_imageview_bg_error_loading_min, context.theme)
    private fun getDefaultPlaceholderDrawable() =
        ResourcesCompat.getDrawable(resources, R.drawable.mf_imageview_loading_min, context.theme)

    fun spToPx(sp: Float): Int {
        return (sp / resources.displayMetrics.density).toInt()
    }

    companion object {
        private const val ZERO = 0
        private val EMPTY_STRING = ""

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: MFImageView, url: String) {
            imageView.setImgUrl(url)
        }
        @BindingAdapter("mf_image_size")
        @JvmStatic
        fun setImageSize(imageView: MFImageView, size: Float?) {
            imageView.setImageSize(size)
        }
        @BindingAdapter("mf_image_draw")
        @JvmStatic
        fun setDrawable(imageView: MFImageView, drawableImage: Drawable?) {
            imageView.setDrawable(drawableImage)
        }

    }
}