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


class Nhom3BinhSpendingFragment : Fragment() {
    private lateinit var viewModel: Nhom3BinhSpendingViewModel
    private lateinit var binding: Nhom3BinhActivitySpendingBinding
    private var formatDate: SimpleDateFormat = SimpleDateFormat("EEE, YYYY-MM-dd")
    private var spendingItem: SpendingItem = SpendingItem("", "", "", true, "")
    private val REQ_CODE_SPEECH_INPUT = 1 // Kiem tra gia tri tra cua giong noi
    var get_string_voice_input = ArrayList<String>()
    var flash = 0
    private var TAG = "GETDATA"
    private var spendingDele = arrayOf<String?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.nhom3_binh_activity_spending,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(Nhom3BinhSpendingViewModel::class.java)
        if (!viewModel.firstAccess) {
            viewModel.GetCategory(binding.cbSpending.isChecked)
            viewModel.firstAccess = true
        }
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

        binding.calendar.setText(viewModel.formatDate.format(viewModel.dateDefault))
        binding.micro.setOnClickListener(
            View.OnClickListener
            // Ph????ng th???c l???y t??c ?????ng khi nh???n v??o button
            { promptSpeechInput() })

        binding.calendar.text = viewModel.formatDate.format(viewModel.dateDefault)
        binding.micro.setOnClickListener(
            View.OnClickListener
            // Ph????ng th???c l???y t??c ?????ng khi nh???n v??o button
            { promptSpeechInput() })

        var mySpendingAdapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.nhom3_anh_style_spinner, viewModel.spending_new
        )
        mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)

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

                if (mySpendingAdapter.getItem(position).toString() == "Th??m m???c kh??c") {
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
                            viewModel.spendingItem.mode = binding.cbSpending.isChecked
                            Toast.makeText(
                                requireContext(),
                                viewModel.CreateCate(dataAddSpending),
                                Toast.LENGTH_SHORT
                            ).show()

                            if (viewModel.validateAddAction(dataAddSpending)) {
                                val positionAdd = mySpendingAdapter.getPosition("Th??m m???c kh??c")
                                viewModel.spending_new[positionAdd] = dataAddSpending
                                viewModel.spending_new =
                                    viewModel.append(viewModel.spending_new, "Th??m m???c kh??c")
                                viewModel.GetCategory(binding.cbSpending.isChecked)
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
                                mDialogView1.findViewById<EditText>(R.id.addAction).error =
                                    "Kh??ng h???p l???!"

                            }
                        }
                        btnaddCancel.setOnClickListener {
                            binding.dropDownSpending.setSelection(0)
                            addAction.dismiss()
                            addFunction.dismiss()
                        }


                    }
                    btnDelete.setOnClickListener {
                        val builder = AlertDialog.Builder(context)
                        val selectedList = ArrayList<Int>()
                        val selectedListRem = ArrayList<Int>()
                        var spendingMem = arrayOf<String?>()
                        builder.setTitle("X??a m???c")


                        viewModel.spendingRem = arrayOf<String?>()
                        if (viewModel.spending_new.size > 1) {
                            for (index in 0..viewModel.spending_new.size - 2) {
                                //loops all indices
                                viewModel.spendingRem = viewModel.append(
                                    viewModel.spendingRem,
                                    viewModel.spending_new[index].toString()
                                )
                            }
                        } else {
                            viewModel.spendingRem = arrayOf<String?>()
                        }

                        builder.setMultiChoiceItems(
                            viewModel.spendingRem, null
                        ) { dialog, which, isChecked ->
                            if (isChecked) {
                                if (selectedList.size < viewModel.spending_new.size - 1) {
                                    selectedList.add(which)
                                }
                                if (selectedList.size == viewModel.spending_new.size - 1) {
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
                            if (selectedList.size == viewModel.spending_new.size - 2) {
                                Toast.makeText(
                                    context,
                                    "Ph???i c?? ??t nh???t 1 m???c!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
//                            viewModel.spending_new = viewModel.spending_new.toMutableList().apply {
//                                for (index in 0..selectedList.size - 1) {
//                                    remove(spendingMem[selectedList[index]])
//                                    Log.e("a", index.toString())
//                                }
//                            }.toTypedArray()
                            viewModel.spendingItem.mode = binding.cbSpending.isChecked
                            for (index in 0..selectedList.size - 1) {
                                Log.e("mem", spendingMem[selectedList[index]]!!)
                                Toast.makeText(
                                    requireContext(),
                                    viewModel.DeleteCate(spendingMem[selectedList[index]]!!),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            viewModel.GetCategory(binding.cbSpending.isChecked)
                            //viewModel.spending_new = spendingMem
                            mySpendingAdapter = ArrayAdapter<String>(
                                requireActivity(),
                                R.layout.nhom3_anh_style_spinner, viewModel.spending_new
                            )
                            mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)
                            binding.dropDownSpending.adapter = mySpendingAdapter
                        }


                        builder.setNeutralButton("H???y b???") { _, which ->

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
                getRecommend()
                Log.e("After", binding.etDescription.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                flash = 0
                Log.e("Before", binding.etDescription.text.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                flash = 0
                Log.e("Current", binding.etDescription.text.toString())
            }
        })
        binding.btnConfirm.setOnClickListener {
            if (binding.imageView4.isChecked) {
                viewModel.spendingItem.description =
                    "*HN" + binding.etDescription.text.toString().trim()
            } else {
                viewModel.spendingItem.description =
                    "*HT" + binding.etDescription.text.toString().trim()
            }
            viewModel.spendingItem.money = binding.etMoney.text.toString().trim()
            viewModel.spendingItem.mode = binding.cbSpending.isChecked
            viewModel.spendingItem.area = binding.dropDownSpending.selectedItem.toString()
            viewModel.spendingItem.date = binding.calendar.text.toString().trim()
            Toast.makeText(requireContext(), viewModel.CreateExOrIn(), Toast.LENGTH_SHORT).show()
            Log.e(TAG, viewModel.spendingItem.description)
            Log.e(TAG, viewModel.spendingItem.money)
            Log.e(TAG, viewModel.spendingItem.mode.toString())
            Log.e(TAG, viewModel.spendingItem.area)
            Log.e(TAG, viewModel.spendingItem.date)
        }

        binding.cbSpending.setOnClickListener {
            viewModel.GetCategory(binding.cbSpending.isChecked)
            mySpendingAdapter = ArrayAdapter<String>(
                requireActivity(),
                R.layout.nhom3_anh_style_spinner, viewModel.spending_new
            )
            mySpendingAdapter.setDropDownViewResource(R.layout.nhom3_anh_style_spinner)
            binding.dropDownSpending.adapter = mySpendingAdapter
            getRecommend()
        }
    }


    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        //-	B???t ?????u m???t ho???t ?????ng s??? nh???c ng?????i d??ng v??? gi???ng n??i v?? g???i n?? qua qu?? tr??nh nh???n d???ng,
        // sau ???? hi???n th??? k???t qu??? tr??n web ho???c k??ch ho???t m???t lo???i ho???t ?????ng d???a tr??n gi???ng n??i c???a ng?????i d??ng.
        // X??c nh???n ???ng d???ng mu???n g???i y??u c???u.
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // G???i ?? nh???n d???ng nh???ng g?? ng?????i d??ng n??i
        /**intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); */
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        // Hi???n th??? y??u c???u cho ng?????i d??ng chu???n b??? n??i v??o
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN")
        // Ch???n ng??n ng??? n??i v??o: ??? ????y ch???n ti???ng Vi???t
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                getApplicationContext(),
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT
            ).show()
        }
        // N???u kh??ng t??m th???y ho???t ?????ng c???a intent, hi???n th??? ra m??n h??nh 3s d??ng ch??? "Sorry...
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                get_string_voice_input =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!! // reusult1 ???????c l???y gi?? tr??? m???ng chu???i k?? t??? n??i v??o
                binding.etDescription.setText(get_string_voice_input[0]) // Hi???n th??? text ra m??n h??nh
            }
        }
    }

    fun getRecommend() {
        viewModel.getRecommend(binding.etDescription.text.toString())
        Log.e("An", viewModel.cateRecommend)
        for (index in 0..viewModel.spending_new.size - 1) {
            Log.e("AnhTran", viewModel.spending_new[index]!!)
            Log.e("An", viewModel.cateRecommend)
            if (viewModel.spending_new[index] == viewModel.cateRecommend) {
                binding.dropDownSpending.setSelection(index)
            }
        }
    }
}