package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySpendingBinding
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import java.text.SimpleDateFormat
import java.util.*


class Nhom3BinhSpendingActivity: AppCompatActivity() {
    private lateinit var viewModel: Nhom3BinhSpendingViewModel
    private lateinit var binding: Nhom3BinhActivitySpendingBinding
    private var formatDate:SimpleDateFormat = SimpleDateFormat("EEE, dd/MM/YYYY")
    private var spendingItem : SpendingItem = SpendingItem(0,"",formatDate,true,"")
    private val REQ_CODE_SPEECH_INPUT = 1 // Kiem tra gia tri tra cua giong noi
    var get_string_voice_input = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.nhom3_binh_activity_spending)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSpendingViewModel::class.java)
        binding.spendingItem = spendingItem
        binding.editDateSpending.setOnClickListener{
            val getDate = Calendar.getInstance()
            val dialog =  DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                val selectDate = Calendar.getInstance()
                val year = selectDate.set(Calendar.YEAR,mYear)
                val month = selectDate.set(Calendar.MONTH,mMonth)
                val day = selectDate.set(Calendar.DAY_OF_MONTH,mDay)
                val date = viewModel.formatDate.format(selectDate.time)
                viewModel.dateDefault = selectDate.time
                binding.calendar.setText(date)
            },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            dialog.show()

        }
        binding.calendar.setText(viewModel.formatDate.format(viewModel.dateDefault))
        binding.micro.setOnClickListener(
            View.OnClickListener
            // Phương thức lấy tác động khi nhấn vào button
            { promptSpeechInput() })

        val mySpendingAdapter = ArrayAdapter<String>(this,
            R.layout.nhom3_anh_style_spinner,resources.getStringArray(R.array.spending))
        mySpendingAdapter.setDropDownViewResource( R.layout.nhom3_anh_style_spinner)
        binding.dropDownSpending.adapter = mySpendingAdapter
        // Get data Adapter
        Log.e("GetData",binding.dropDownSpending.selectedItem.toString())
        // Set data Adapter
        binding.dropDownSpending.setSelection(0)
    }
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        //-	Bắt đầu một hoạt động sẽ nhắc người dùng về giọng nói và gửi nó qua quá trình nhận dạng,
        // sau đó hiển thị kết quả trên web hoặc kích hoạt một loại hoạt động dựa trên giọng nói của người dùng.
        // Xác nhận ứng dụng muốn gửi yêu cầu.
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // Gợi ý nhận dạng những gì người dùng nói
        /**intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); */
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        // Hiển thị yêu cầu cho người dùng chuẩn bị nói vào
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN")
        // Chọn ngôn ngữ nói vào: ở đây chọn tiếng Việt
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                getApplicationContext(),
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT
            ).show()
        }
        // Nếu không tìm thấy hoạt động của intent, hiển thị ra màn hình 3s dòng chữ "Sorry...
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> if (resultCode == RESULT_OK && data != null) {
                get_string_voice_input =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!! // reusult1 được lấy giá trị mảng chuỗi kí tự nói vào
                binding.etDescription.setText(get_string_voice_input[0]) // Hiển thị text ra màn hình
            }
        }
    }
}
