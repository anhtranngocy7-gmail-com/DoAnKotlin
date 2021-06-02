package com.laptrinhdidong.nhom3.quanlichitieu.Thongke.ReportExpense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.laptrinhdidong.nhom3.quanlichitieu.MainApp.TichLuy.Nhom3AnTichLuyAdapter
import com.laptrinhdidong.nhom3.quanlichitieu.R
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnFragmentReportexpenseBinding
import com.laptrinhdidong.nhom3.quanlichitieu.databinding.Nhom3AnTichluyFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Nhom3AnReportExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Nhom3AnReportExpenseFragment : Fragment() {
    private lateinit var binding: Nhom3AnFragmentReportexpenseBinding
    private lateinit var outerList : MutableList<Nhom3AnGroupReportExpense>
    private lateinit var map : MutableMap<Nhom3AnGroupReportExpense,MutableList<Nhom3AnItemReportExpense>>
    private lateinit var adapter: Nhom3AnOuterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<Nhom3AnFragmentReportexpenseBinding>(
            inflater,
            R.layout.nhom3_an_fragment_reportexpense,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assignData()
        adapter = Nhom3AnOuterAdapter(requireContext())
        binding.rcvOuter.layoutManager = LinearLayoutManager(context)
        adapter.lstOut=outerList
        adapter.map=map
        binding.rcvOuter.adapter=adapter
    }
    fun assignData()
    {
        outerList= mutableListOf(
            Nhom3AnGroupReportExpense("Ăn uống","1000vnd"),
            Nhom3AnGroupReportExpense("Đi lại","1000vnd")
        )
        map= mutableMapOf()
        val topic1 : MutableList<Nhom3AnItemReportExpense> = ArrayList()
        topic1.add(Nhom3AnItemReportExpense("ăn sáng","10000"))
        topic1.add(Nhom3AnItemReportExpense("ăn trưa","20000"))
        val topic2 : MutableList<Nhom3AnItemReportExpense> = ArrayList()
        topic2.add(Nhom3AnItemReportExpense("đổ xăng","10000"))
        topic2.add(Nhom3AnItemReportExpense("gửi xe","20000"))
        map[outerList[0]]=topic1
        map[outerList[1]]=topic2

    }
}