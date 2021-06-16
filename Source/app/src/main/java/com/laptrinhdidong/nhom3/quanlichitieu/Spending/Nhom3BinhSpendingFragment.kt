package com.laptrinhdidong.nhom3.quanlichitieu.Spending

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.SpendingItem
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3BinhActivitySpendingBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Nhom3BinhSpendingFragment: Fragment()  {

    private lateinit var viewModel: Nhom3BinhSpendingViewModel
    private lateinit var binding: Nhom3BinhActivitySpendingBinding
    private var formatDate: SimpleDateFormat = SimpleDateFormat("EEE, YYYY-MM-dd")
    private var spendingItem : SpendingItem = SpendingItem("","","",true,"")
    private val REQ_CODE_SPEECH_INPUT = 1 // Kiem tra gia tri tra cua giong noi
    var get_string_voice_input = ArrayList<String>()
    private var TAG = "GETDATA"
    private var spendingDele = arrayOf<String?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.nhom3_binh_activity_spending,container,false)

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSpendingViewModel::class.java)
        binding.spendingItem = spendingItem
        binding.editDateSpending.setOnClickListener {
            val getDate = Calendar.getInstance()
            val dialog = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val selectDate = Calendar.getInstance()
                    val year = selectDate.set(Calendar.YEAR, mYear)
                    val month = selectDate.set(Calendar.MONTH, mMonth)
                    val day = selectDate.set(Calendar.DAY_OF_MONTH, mDay)
                    val date = viewModel.formatDate.format(selectDate.time)
                    viewModel.dateDefault = selectDate.time
                    binding.calendar.text = date
                },
                getDate.get(Calendar.YEAR),
                getDate.get(Calendar.MONTH),
                getDate.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
        binding.calendar.text = viewModel.formatDate.format(viewModel.dateDefault)
            binding.micro.setOnClickListener(
                View.OnClickListener
                // Phương thức lấy tác động khi nhấn vào button
                { promptSpeechInput() })

        var mySpendingAdapter = ArrayAdapter<String>(requireActivity(),
            R.layout.nhom3_anh_style_spinner,viewModel.spending_new)
        mySpendingAdapter.setDropDownViewResource( R.layout.nhom3_anh_style_spinner)

        binding.dropDownSpending.adapter = mySpendingAdapter
        var mDialogView = LayoutInflater.from(context)
            .inflate(R.layout.nhom3_binh_custom_dialog, null)
        var mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val addFunction: AlertDialog = mBuilder.create()

        var mDialogView1 = LayoutInflater.from(context)
            .inflate(R.layout.nhom3_binh_custom_dialog_add, null)
        var mBuilder1 = AlertDialog.Builder(context)
            .setView(mDialogView1)
        val addAction: AlertDialog = mBuilder1.create()

        binding.dropDownSpending.onItemSelectedListener = object : OnItemSelectedListener {
            @SuppressLint("ResourceType")
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {

                if (mySpendingAdapter.getItem(position).toString() == "Thêm mục khác") {
                    binding.dropDownSpending.selectedView
                    addFunction.show()
                    val btnAdd = mDialogView.findViewById<Button>(R.id.btnAdd)
                    val btnOut = mDialogView.findViewById<Button>(R.id.btnOut)
                    val btnDelete = mDialogView.findViewById<Button>(R.id.btnDelete)
                    btnAdd.setOnClickListener {
                        addAction.show()
                        val btnaddConfirm = mDialogView1.findViewById<Button>(R.id.addConfirm)
                        val btnaddCancel = mDialogView1.findViewById<Button>(R.id.addCancel)
                        btnaddConfirm.setOnClickListener {
                            val dataAddSpending =
                                mDialogView1.findViewById<EditText>(R.id.addAction).text.toString()
                            if (viewModel.validateAddAction(dataAddSpending)) {
                                val positionAdd = mySpendingAdapter.getPosition("Thêm mục khác")
                                viewModel.spending_new[positionAdd] = dataAddSpending
                                viewModel.spending_new = viewModel.append(viewModel.spending_new, "Thêm mục khác")

                                mySpendingAdapter = ArrayAdapter<String>(
                                    requireActivity(),
                                    R.layout.nhom3_anh_style_spinner, viewModel.spending_new
                                )
                                mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)
                                binding.dropDownSpending.adapter = mySpendingAdapter
                                binding.dropDownSpending.setSelection(positionAdd)
                                mDialogView1.findViewById<EditText>(R.id.addAction).setText("")
                                addAction.dismiss()
                                addFunction.dismiss()
                            } else {
                                mDialogView1.findViewById<EditText>(R.id.addAction).error = "Không hợp lệ!"

                            }
                        }
                        btnaddCancel.setOnClickListener {
                            binding.dropDownSpending.setSelection(0)
                            addAction.dismiss()
                            addFunction.dismiss()
                        }


                    }
                    btnDelete.setOnClickListener{
                        val builder = AlertDialog.Builder(context)
                        val selectedList = ArrayList<Int>()
                        val selectedListRem = ArrayList<Int>()
                        var spendingMem  = arrayOf<String?>()
                        builder.setTitle("Xóa mục")


                        viewModel.spendingRem = arrayOf<String?>()
                        if (viewModel.spending_new.size > 1)
                        {
                            for (index in 0..viewModel.spending_new.size - 2) {
                                //loops all indices
                                viewModel.spendingRem = viewModel.append(viewModel.spendingRem,viewModel.spending_new[index].toString())
                            }
                        }
                        else{
                            viewModel.spendingRem = arrayOf<String?>()
                        }

                        builder.setMultiChoiceItems(viewModel.spendingRem , null
                        ) { dialog, which, isChecked ->
                            if (isChecked) {
                                if(selectedList.size < viewModel.spending_new.size - 1) {
                                       selectedList.add(which)
                                }
                                if(selectedList.size == viewModel.spending_new.size - 1)
                                {
                                    selectedList.remove(Integer.valueOf(which))
                                }
                            } else if (selectedList.contains(which)) {
                                selectedList.remove(Integer.valueOf(which))
                            }
                        }
                        builder.setPositiveButton("OK") { dialogInterface, i ->
                            val selectedStrings = ArrayList<String>()
                            spendingMem = viewModel.spending_new
                            for (j in selectedList.indices) {
                                viewModel.spending_new[selectedList[j]]?.let { it1 ->
                                    selectedStrings.add(
                                        it1
                                    )
                                }
                            }
                            if(selectedList.size == viewModel.spending_new.size - 2)
                            {
                                Toast.makeText(context, "Phải có ít nhất 1 mục!", Toast.LENGTH_SHORT).show()
                            }
                            viewModel.spending_new = viewModel.spending_new.toMutableList().apply {
                                for (index in 0..selectedList.size - 1) {
                                    remove(spendingMem[selectedList[index]])
                                    Log.e("a", index.toString())
                                }
                            }.toTypedArray()

                           //viewModel.spending_new = spendingMem
                            mySpendingAdapter = ArrayAdapter<String>(
                                requireActivity(),
                                R.layout.nhom3_anh_style_spinner, viewModel.spending_new
                            )
                            mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)
                            binding.dropDownSpending.adapter = mySpendingAdapter
                        }


                        builder.setNeutralButton("Hủy bỏ"){ _, which->

                        }
                        builder.show()

                    }
                    btnOut.setOnClickListener {
                        binding.dropDownSpending.setSelection(0)
                        addFunction.dismiss()
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.e("After",binding.etDescription.text.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("Before",binding.etDescription.text.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("Current",binding.etDescription.text.toString())
            }
        })
            binding.btnConfirm.setOnClickListener {
                if(binding.imageView4.isChecked) {
                    viewModel.spendingItem.description =
                       "*Hàng ngày"+ binding.etDescription.text.toString().trim()
                }
                else{
                    viewModel.spendingItem.description =
                        "*Hàng tháng "+ binding.etDescription.text.toString().trim()
                }
                viewModel.spendingItem.money = binding.etMoney.text.toString().trim()
                viewModel.spendingItem.mode = binding.imageView5.isChecked
                viewModel.spendingItem.area = binding.dropDownSpending.selectedItem.toString()
                viewModel.spendingItem.date = binding.calendar.text.toString().trim()

                Log.e(TAG,viewModel.spendingItem.description)
                Log.e(TAG,viewModel.spendingItem.money)
                Log.e(TAG,viewModel.spendingItem.mode.toString())
                Log.e(TAG,viewModel.spendingItem.area)
                Log.e(TAG,viewModel.spendingItem.date)
            }















//        var mDialogView = LayoutInflater.from(context)
//            .inflate(R.layout.nhom3_binh_custom_dialog, null)
//        var mBuilder = AlertDialog.Builder(context)
//            .setView(mDialogView)
//            .setTitle("Thêm chức năng")
//        val alert11: AlertDialog = mBuilder.create()
//        binding.dropDownSpending.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(
//                parentView: AdapterView<*>?,
//                selectedItemView: View,
//                position: Int,
//                id: Long
//            ) {
//                Log.e("demdautien", viewModel.spending_new.size.toString())
//                Log.e("vitriphantu", (position).toString())
//                Log.e("vip", mySpendingAdapter.getItem(position).toString())
//                if (mySpendingAdapter.getItem(position).toString() == "Thêm mục khác") {
//                    binding.dropDownSpending.selectedView
//                    alert11.show()
//                    val btn = mDialogView.findViewById<Button>(R.id.DialogConfirm)
//                    val btnCancel = mDialogView.findViewById<Button>(R.id.DialogCancel)
//                    btn.setOnClickListener {
//                        val dataAddSpending =
//                            mDialogView.findViewById<EditText>(R.id.addAction).text.toString()
//                        if (viewModel.validateAddAction(dataAddSpending)) {
//                            val positionAdd = mySpendingAdapter.getPosition("Thêm mục khác")
//                            viewModel.spending_new[positionAdd] = dataAddSpending
//                            viewModel.spending_new = viewModel.append(viewModel.spending_new, "Thêm mục khác")
//
//                            mySpendingAdapter = ArrayAdapter<String>(
//                                requireActivity(),
//                                R.layout.nhom3_anh_style_spinner, viewModel.spending_new
//                            )
//                            mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)
//                            binding.dropDownSpending.adapter = mySpendingAdapter
//                            binding.dropDownSpending.setSelection(positionAdd)
//                            mDialogView.findViewById<EditText>(R.id.addAction).setText("")
//                            alert11.dismiss()
//                        } else {
//                            mDialogView.findViewById<EditText>(R.id.addAction).error = "Không hợp lệ!"
//
//                        }
//                    }
//                    btnCancel.setOnClickListener {
//                        binding.dropDownSpending.setSelection(0)
//                        mDialogView.findViewById<EditText>(R.id.addAction).setText("")
//                        alert11.dismiss()
//                    }
//                }
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//            }
//        }
//        binding.etDescription.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                Log.e("After",binding.etDescription.text.toString())
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Log.e("Before",binding.etDescription.text.toString())
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Log.e("Current",binding.etDescription.text.toString())
//            }
//        })
//            binding.btnConfirm.setOnClickListener {
//                viewModel.spendingItem.description = binding.etDescription.text.toString().trim()
//                viewModel.spendingItem.money = binding.etMoney.text.toString().trim()
//                viewModel.spendingItem.mode = binding.imageView5.isChecked
//                viewModel.spendingItem.area = binding.dropDownSpending.selectedItem.toString()
//                viewModel.spendingItem.date = binding.calendar.text.toString().trim()
//
//                Log.e(TAG,viewModel.spendingItem.description)
//                Log.e(TAG,viewModel.spendingItem.money)
//                Log.e(TAG,viewModel.spendingItem.mode.toString())
//                Log.e(TAG,viewModel.spendingItem.area)
//                Log.e(TAG,viewModel.spendingItem.date)
//            }
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
            REQ_CODE_SPEECH_INPUT -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                get_string_voice_input =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!! // reusult1 được lấy giá trị mảng chuỗi kí tự nói vào
                binding.etDescription.setText(get_string_voice_input[0]) // Hiển thị text ra màn hình
            }
        }
    }
}