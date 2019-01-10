package com.sopt.appjam_sggsag

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sopt.appjam_sggsag.Data.DetailPosterData
import com.sopt.appjam_sggsag.Data.PosterData

class CardStackAdapter(
//    private var posters: List<DetailPosterData> = emptyList()
//    var posters: List<DetailPosterData> = emptyList()
    var posters: ArrayList<DetailPosterData>
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    //    private var homeFragmentView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        homeFragmentView=inflater!!.inflate(R.layout.fragment_home,parent,false)
        return ViewHolder(
            inflater.inflate(R.layout.item_spot, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poster = posters[position]
//        holder.name.text = "${spot.posterIdx}. ${spot.photoUrl}"
//        holder.city.text = spot.outline

        //Count process
        var processCnt=0
        if (poster.documentDate != null){
            processCnt++
        }
        if (poster.announceDate1 != null){
            processCnt++
        }
        if (poster.interviewDate != null){
            processCnt++
        }
        if (poster.finalAnnounceDate != null){
            processCnt++
        }

        Glide.with(holder.image)
            .load(poster.photoUrl)
            .into(holder.image)
        //for fix error of background of second tab->now it's not error
        //우선 item_spot에서 FrameLayout끼리 있는 곳에 놔줘야해. CardView보다 텍스트 상 위에 놓으면 View상으로 밑면에 놓이게 돼.
        //FrameLayout있는 곳에 second tab을 놔두고, 이게 라이브러리 내부 로직에 있지 않으므로 다른 itme_spot안의 view들처럼 기본적으로 안 보이지 않아.
        //그러니까 INVISIBLE 처리해주고, 클릭할 때만 VISIBLE해주는 것으로. 그리고 왼쪽을 누르면 다시 INVISIBLE.
        //bringToFront가 holder.image에만 먹히고, holder.name, holer.city에 안 먹는 이유, holer.cardContentBackground에 제대로 안 먹는 이유는 미지수
        //일부만 잘려서 움직이던데 같이 엮인 TextView 및 음수 margin값 때문일 것 같다만 이 부분은 실험 안 해봄.

        //check detail_information

        setDetailViewInvisible(holder,1)
        setDetailViewInvisible(holder,2)
        setDetailViewInvisible(holder,3)
        setDetailViewInvisible(holder,4)

        //for card information
//        holder.tmpcontent1.text = spot.posterName
//        holder.tmpcontent2.text = spot.posterName


        var widthOfCard=holder.image.width
        var xAtDown : Float
        var xAtUp :Float
//        var isClick : Boolean = false
        holder.itemView.setOnTouchListener { v, event ->
            when (event.action) {
                //true로 하면 다음으로 내려간다. false를 하면 거기서 끝나버려
                //눌렀다가 바로 떼려해도 미세하게 움직이면 그걸 누른채 움직이는 걸로 감지하는 동시에 떼는 걸로 감지함
                //그런데 작정하고 움직이면 떼는 것을 감지 안 함
                //ACTION_DOWN이 없으면 ACTION_UP이 감지가 안 되네
                //isClick 초기화는 안 해도 되는 듯
                MotionEvent.ACTION_DOWN-> {
                    xAtDown = event.getX()
                    Toast.makeText(v.context,poster.posterIdx.toString(),Toast.LENGTH_SHORT).show()
                    return@setOnTouchListener true
                }
                /*
                MotionEvent.ACTION_MOVE->{
                    Toast.makeText(v.context,"누른 채 움직일 때",Toast.LENGTH_SHORT).show()
                    return@setOnTouchListener false
                }
                */
                MotionEvent.ACTION_UP->{//클릭
//                    isClick = true
                    xAtUp = event.getX()

                    //detail_number 먼저 확인하고 들어가자
                    if (xAtUp<=widthOfCard/2) {//card의 왼쪽 클릭
                        if (processCnt==1){
                            setDetailViewInvisible(holder,1)
                            holder.detail2_step1.visibility=View.INVISIBLE
                            holder.detail2_step1_date.visibility=View.INVISIBLE
                        }
                        else if (processCnt==2){
//                            holder.detail2_step1_date.text=poster.documentDate
//                            holder.detail2_step2_date.text=(poster.announceDate1+poster.interviewDate+poster.finalAnnounceDate)
                            setDetailViewInvisible(holder,2)
                            holder.detail2_step1.visibility=View.INVISIBLE
                            holder.detail2_step1_date.visibility=View.INVISIBLE
                            holder.detail2_step2.visibility=View.INVISIBLE
                            holder.detail2_step2_date.visibility=View.INVISIBLE
                        }
                        else if (processCnt==3){
//                            holder.detail3_step1_date.text=poster.documentDate
//                            holder.detail3_step2_date.text=poster.announceDate1+poster.interviewDate
//                            holder.detail3_step3_date.text=poster.finalAnnounceDate
                            setDetailViewInvisible(holder,3)
                            holder.detail3_step1.visibility=View.INVISIBLE
                            holder.detail3_step1_date.visibility=View.INVISIBLE
                            holder.detail3_step2.visibility=View.INVISIBLE
                            holder.detail3_step2_date.visibility=View.INVISIBLE
                            holder.detail3_step3.visibility=View.INVISIBLE
                            holder.detail3_step3_date.visibility=View.INVISIBLE
                        }
                        else{//processCnt==4
//                            holder.detail4_step1_date.text=poster.documentDate
//                            holder.detail4_step2_date.text=poster.announceDate1
//                            holder.detail4_step3_date.text=poster.interviewDate
//                            holder.detail4_step4_date.text=poster.finalAnnounceDate
                            setDetailViewInvisible(holder,4)
                            holder.detail4_step1.visibility=View.INVISIBLE
                            holder.detail4_step1_date.visibility=View.INVISIBLE
                            holder.detail4_step2.visibility=View.INVISIBLE
                            holder.detail4_step2_date.visibility=View.INVISIBLE
                            holder.detail4_step3.visibility=View.INVISIBLE
                            holder.detail4_step3_date.visibility=View.INVISIBLE
                            holder.detail4_step4.visibility=View.INVISIBLE
                            holder.detail4_step4_date.visibility=View.INVISIBLE
                        }
                    }
                    else{//card의 오른쪽 클릭
                        if (processCnt==1){
                            holder.detail1_step1_date.text=poster.documentDate
                            setDetailByCategory(holder,poster.categoryIdx,poster)
                            setDetailViewVisible(holder,1)
                        }
                        else if (processCnt==2){
                            holder.detail2_step1_date.text=poster.documentDate
                            holder.detail2_step2_date.text=(poster.announceDate1+poster.interviewDate+poster.finalAnnounceDate)
                            setDetailByCategory(holder,poster.categoryIdx,poster)
                            setDetailViewVisible(holder,2)
//                            holder.detail2_step1.visibility=View.VISIBLE
//                            holder.detail2_step1_date.visibility=View.VISIBLE
//                            holder.detail2_step2.visibility=View.VISIBLE
//                            holder.detail2_step2_date.visibility=View.VISIBLE
                        }
                        else if (processCnt==3){
                            holder.detail3_step1_date.text=poster.documentDate
                            holder.detail3_step2_date.text=poster.announceDate1+poster.interviewDate
                            holder.detail3_step3_date.text=poster.finalAnnounceDate
                            setDetailByCategory(holder,poster.categoryIdx,poster)
                            setDetailViewVisible(holder,3)
//                            holder.detail3_step1.visibility=View.VISIBLE
//                            holder.detail3_step1_date.visibility=View.VISIBLE
//                            holder.detail3_step2.visibility=View.VISIBLE
//                            holder.detail3_step2_date.visibility=View.VISIBLE
//                            holder.detail3_step3.visibility=View.VISIBLE
//                            holder.detail3_step3_date.visibility=View.VISIBLE
                        }
                        else{//processCnt==4
                            holder.detail4_step1_date.text=poster.documentDate
                            holder.detail4_step2_date.text=poster.announceDate1
                            holder.detail4_step3_date.text=poster.interviewDate
                            holder.detail4_step4_date.text=poster.finalAnnounceDate
                            setDetailByCategory(holder,poster.categoryIdx,poster)
                            setDetailViewVisible(holder,4)
//                            holder.detail4_step1.visibility=View.VISIBLE
//                            holder.detail4_step1_date.visibility=View.VISIBLE
//                            holder.detail4_step2.visibility=View.VISIBLE
//                            holder.detail4_step2_date.visibility=View.VISIBLE
//                            holder.detail4_step3.visibility=View.VISIBLE
//                            holder.detail4_step3_date.visibility=View.VISIBLE
//                            holder.detail4_step4.visibility=View.VISIBLE
//                            holder.detail4_step4_date.visibility=View.VISIBLE
                        }
                    }
                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
//            v?.onTouchEvent(event) ?: true
        }
    }

    override fun getItemCount(): Int {
        return posters.size
    }

    fun setSpots(posters: ArrayList<DetailPosterData>) {
        this.posters = posters
    }

    fun getSpots(): ArrayList<DetailPosterData> {
        return posters
    }

    fun setDetailViewInvisible(holder: ViewHolder, processCnt : Int){
        if (processCnt==1){
            holder.detail1_title.visibility=View.INVISIBLE
            holder.detail1_hash.visibility=View.INVISIBLE
            holder.detail1_rectangle.visibility=View.INVISIBLE
            holder.detail1_process_paper .visibility=View.INVISIBLE
            holder.detail1_step1.visibility=View.INVISIBLE
            holder.detail1_step1_date .visibility=View.INVISIBLE
            holder.detail1_title_summary.visibility=View.INVISIBLE
            holder.detail1_text_summary.visibility=View.INVISIBLE
            holder.detail1_title_target.visibility=View.INVISIBLE
            holder.detail1_text_target.visibility=View.INVISIBLE
            holder.detail1_title_term.visibility=View.INVISIBLE
            holder.detail1_text_term.visibility=View.INVISIBLE
            holder.detail1_title_benefit.visibility=View.INVISIBLE
            holder.detail1_text_benefit.visibility=View.INVISIBLE
        }
        else if (processCnt==2){
            holder.detail2_title.visibility=View.INVISIBLE
            holder.detail2_hash.visibility=View.INVISIBLE
            holder.detail2_rectangle.visibility=View.INVISIBLE
            holder.detail2_process_paper.visibility=View.INVISIBLE
            holder.detail2_process_line.visibility=View.INVISIBLE
            holder.detail2_process_final.visibility=View.INVISIBLE
            holder.detail2_step1.visibility=View.INVISIBLE
            holder.detail2_step1_date.visibility=View.INVISIBLE
            holder.detail2_step2.visibility=View.INVISIBLE
            holder.detail2_step2_date.visibility=View.INVISIBLE
            holder.detail2_title_summary.visibility=View.INVISIBLE
            holder.detail2_text_summary.visibility=View.INVISIBLE
            holder.detail2_title_target.visibility=View.INVISIBLE
            holder.detail2_text_target.visibility=View.INVISIBLE
            holder.detail2_title_term.visibility=View.INVISIBLE
            holder.detail2_text_term.visibility=View.INVISIBLE
            holder.detail2_title_benefit.visibility=View.INVISIBLE
            holder.detail2_text_benefit.visibility=View.INVISIBLE
        }
        else if (processCnt==3){
            holder.detail3_title.visibility=View.INVISIBLE
            holder.detail3_hash.visibility=View.INVISIBLE
            holder.detail3_rectangle.visibility=View.INVISIBLE
            holder.detail3_process_paper.visibility=View.INVISIBLE
            holder.detail3_process_line.visibility=View.INVISIBLE
            holder.detail3_process_interview.visibility=View.INVISIBLE
            holder.detail3_process_final.visibility=View.INVISIBLE
            holder.detail3_step1.visibility=View.INVISIBLE
            holder.detail3_step1_date.visibility=View.INVISIBLE
            holder.detail3_step2.visibility=View.INVISIBLE
            holder.detail3_step2_date.visibility=View.INVISIBLE
            holder.detail3_step3.visibility=View.INVISIBLE
            holder.detail3_step3_date.visibility=View.INVISIBLE
            holder.detail3_title_summary.visibility=View.INVISIBLE
            holder.detail3_text_summary.visibility=View.INVISIBLE
            holder.detail3_title_target.visibility=View.INVISIBLE
            holder.detail3_text_target.visibility=View.INVISIBLE
            holder.detail3_title_term.visibility=View.INVISIBLE
            holder.detail3_text_term.visibility=View.INVISIBLE
            holder.detail3_title_benefit.visibility=View.INVISIBLE
            holder.detail3_text_benefit.visibility=View.INVISIBLE
        }
        else{//processCnt==4
            holder.detail4_title.visibility=View.INVISIBLE
            holder.detail4_hash .visibility=View.INVISIBLE
            holder.detail4_rectangle.visibility=View.INVISIBLE
            holder.detail4_process_paper.visibility=View.INVISIBLE
            holder.detail4_process_line.visibility=View.INVISIBLE
            holder.detail4_process_bell.visibility=View.INVISIBLE
            holder.detail4_process_interview .visibility=View.INVISIBLE
            holder.detail4_process_final .visibility=View.INVISIBLE
            holder.detail4_step1.visibility=View.INVISIBLE
            holder.detail4_step1_date.visibility=View.INVISIBLE
            holder.detail4_step2 .visibility=View.INVISIBLE
            holder.detail4_step2_date.visibility=View.INVISIBLE
            holder.detail4_step3 .visibility=View.INVISIBLE
            holder.detail4_step3_date .visibility=View.INVISIBLE
            holder.detail4_step4 .visibility=View.INVISIBLE
            holder.detail4_step4_date.visibility=View.INVISIBLE
            holder.detail4_title_summary .visibility=View.INVISIBLE
            holder.detail4_text_summary .visibility=View.INVISIBLE
            holder.detail4_title_target.visibility=View.INVISIBLE
            holder.detail4_text_target .visibility=View.INVISIBLE
            holder.detail4_title_term .visibility=View.INVISIBLE
            holder.detail4_text_term .visibility=View.INVISIBLE
            holder.detail4_title_benefit .visibility=View.INVISIBLE
            holder.detail4_text_benefit .visibility=View.INVISIBLE
        }
    }

    fun setDetailViewVisible(holder: ViewHolder,processCnt : Int){
        if (processCnt==1){
            holder.detail1_title.visibility=View.VISIBLE
            holder.detail1_hash.visibility=View.VISIBLE
            holder.detail1_rectangle.visibility=View.VISIBLE
            holder.detail1_process_paper .visibility=View.VISIBLE
            holder.detail1_step1.visibility=View.VISIBLE
            holder.detail1_step1_date .visibility=View.VISIBLE
            holder.detail1_title_summary.visibility=View.VISIBLE
            holder.detail1_text_summary.visibility=View.VISIBLE
            holder.detail1_title_target.visibility=View.VISIBLE
            holder.detail1_text_target.visibility=View.VISIBLE
            holder.detail1_title_term.visibility=View.VISIBLE
            holder.detail1_text_term.visibility=View.VISIBLE
            holder.detail1_title_benefit.visibility=View.VISIBLE
            holder.detail1_text_benefit.visibility=View.VISIBLE
        }
        else if (processCnt==2){

            holder.detail2_title.visibility=View.VISIBLE
            holder.detail2_hash.visibility=View.VISIBLE
            holder.detail2_rectangle.visibility=View.VISIBLE
            holder.detail2_process_paper.visibility=View.VISIBLE
            holder.detail2_process_line.visibility=View.VISIBLE
            holder.detail2_process_final.visibility=View.VISIBLE
            holder.detail2_step1.visibility=View.VISIBLE
            holder.detail2_step1_date.visibility=View.VISIBLE
            holder.detail2_step2.visibility=View.VISIBLE
            holder.detail2_step2_date.visibility=View.VISIBLE
            holder.detail2_title_summary.visibility=View.VISIBLE
            holder.detail2_text_summary.visibility=View.VISIBLE
            holder.detail2_title_target.visibility=View.VISIBLE
            holder.detail2_text_target.visibility=View.VISIBLE
            holder.detail2_title_term.visibility=View.VISIBLE
            holder.detail2_text_term.visibility=View.VISIBLE
            holder.detail2_title_benefit.visibility=View.VISIBLE
            holder.detail2_text_benefit.visibility=View.VISIBLE
        }
        else if (processCnt==3){
            holder.detail3_title.visibility=View.VISIBLE
            holder.detail3_hash.visibility=View.VISIBLE
            holder.detail3_rectangle.visibility=View.VISIBLE
            holder.detail3_process_paper.visibility=View.VISIBLE
            holder.detail3_process_line.visibility=View.VISIBLE
            holder.detail3_process_interview.visibility=View.VISIBLE
            holder.detail3_process_final.visibility=View.VISIBLE
            holder.detail3_step1.visibility=View.VISIBLE
            holder.detail3_step1_date.visibility=View.VISIBLE
            holder.detail3_step2.visibility=View.VISIBLE
            holder.detail3_step2_date.visibility=View.VISIBLE
            holder.detail3_step3.visibility=View.VISIBLE
            holder.detail3_step3_date.visibility=View.VISIBLE
            holder.detail3_title_summary.visibility=View.VISIBLE
            holder.detail3_text_summary.visibility=View.VISIBLE
            holder.detail3_title_target.visibility=View.VISIBLE
            holder.detail3_text_target.visibility=View.VISIBLE
            holder.detail3_title_term.visibility=View.VISIBLE
            holder.detail3_text_term.visibility=View.VISIBLE
            holder.detail3_title_benefit.visibility=View.VISIBLE
            holder.detail3_text_benefit.visibility=View.VISIBLE
        }
        else{//processCnt==4
            holder.detail4_title.visibility=View.VISIBLE
            holder.detail4_hash .visibility=View.VISIBLE
            holder.detail4_rectangle.visibility=View.VISIBLE
            holder.detail4_process_paper.visibility=View.VISIBLE
            holder.detail4_process_line.visibility=View.VISIBLE
            holder.detail4_process_bell.visibility=View.VISIBLE
            holder.detail4_process_interview .visibility=View.VISIBLE
            holder.detail4_process_final .visibility=View.VISIBLE
            holder.detail4_step1.visibility=View.VISIBLE
            holder.detail4_step1_date.visibility=View.VISIBLE
            holder.detail4_step2 .visibility=View.VISIBLE
            holder.detail4_step2_date.visibility=View.VISIBLE
            holder.detail4_step3 .visibility=View.VISIBLE
            holder.detail4_step3_date .visibility=View.VISIBLE
            holder.detail4_step4 .visibility=View.VISIBLE
            holder.detail4_step4_date.visibility=View.VISIBLE
            holder.detail4_title_summary .visibility=View.VISIBLE
            holder.detail4_text_summary .visibility=View.VISIBLE
            holder.detail4_title_target.visibility=View.VISIBLE
            holder.detail4_text_target .visibility=View.VISIBLE
            holder.detail4_title_term .visibility=View.VISIBLE
            holder.detail4_text_term .visibility=View.VISIBLE
            holder.detail4_title_benefit .visibility=View.VISIBLE
            holder.detail4_text_benefit .visibility=View.VISIBLE
        }
    }

    fun setDetailByCategory(holder : ViewHolder,categoryIdx : Int, poster : DetailPosterData){
        if (categoryIdx==0){//공모전
            holder.detail1_title_summary.text="개요"
            holder.detail1_title_target.text="공모주제"
            holder.detail1_title_term.text="대상"
            holder.detail1_title_benefit.text="시상내역"
            holder.detail2_title_summary.text="개요"
            holder.detail2_title_target.text="공모주제"
            holder.detail2_title_term.text="대상"
            holder.detail2_title_benefit.text="시상내역"
            holder.detail3_title_summary.text="개요"
            holder.detail3_title_target.text="공모주제"
            holder.detail3_title_term.text="대상"
            holder.detail3_title_benefit.text="시상내역"
            holder.detail4_title_summary.text="개요"
            holder.detail4_title_target.text="공모주제"
            holder.detail4_title_term.text="대상"
            holder.detail4_title_benefit.text="시상내역"

            holder.detail1_text_summary.text=poster.outline
            holder.detail1_text_target.text=poster.period
            holder.detail1_text_term.text=poster.target
            holder.detail1_text_benefit.text=poster.benefit
            holder.detail2_text_summary.text=poster.outline
            holder.detail2_text_target.text=poster.period
            holder.detail2_text_term.text=poster.target
            holder.detail2_text_benefit.text=poster.benefit
            holder.detail3_text_summary.text=poster.outline
            holder.detail3_text_target.text=poster.period
            holder.detail3_text_term.text=poster.target
            holder.detail3_text_benefit.text=poster.benefit
            holder.detail4_text_summary.text=poster.outline
            holder.detail4_text_target.text=poster.period
            holder.detail4_text_term.text=poster.target
            holder.detail4_text_benefit.text=poster.benefit
        }
        else if(categoryIdx==1){//대외활동
            holder.detail1_title_summary.text="활동내용"
            holder.detail1_title_target.text="대상"
            holder.detail1_title_term.text="활동기간"
            holder.detail1_title_benefit.text="활동혜택"
            holder.detail2_title_summary.text="활동내용"
            holder.detail2_title_target.text="대상"
            holder.detail2_title_term.text="활동기간"
            holder.detail2_title_benefit.text="시상내역"
            holder.detail3_title_summary.text="활동혜택"
            holder.detail3_title_target.text="대상"
            holder.detail3_title_term.text="활동혜택"
            holder.detail3_title_benefit.text="시상내역"
            holder.detail4_title_summary.text="활동내용"
            holder.detail4_title_target.text="대상"
            holder.detail4_title_term.text="활동기간"
            holder.detail4_title_benefit.text="활동혜택"

            holder.detail1_text_summary.text=poster.outline
            holder.detail1_text_target.text=poster.target
            holder.detail1_text_term.text=poster.period
            holder.detail1_text_benefit.text=poster.benefit
            holder.detail2_text_summary.text=poster.outline
            holder.detail2_text_target.text=poster.target
            holder.detail2_text_term.text=poster.period
            holder.detail2_text_benefit.text=poster.benefit
            holder.detail3_text_summary.text=poster.outline
            holder.detail3_text_target.text=poster.target
            holder.detail3_text_term.text=poster.period
            holder.detail3_text_benefit.text=poster.benefit
            holder.detail4_text_summary.text=poster.outline
            holder.detail4_text_target.text=poster.target
            holder.detail4_text_term.text=poster.period
            holder.detail4_text_benefit.text=poster.benefit
        }
        else if(categoryIdx==2){//동아리
            holder.detail1_title_summary.text="동아리 소개"
            holder.detail1_title_target.text="활동지역 및 시간"
            holder.detail1_title_term.text="지원자격"
            holder.detail2_title_summary.text="동아리 소개"
            holder.detail2_title_target.text="활동지역 및 시간"
            holder.detail2_title_term.text="지원자격"
            holder.detail3_title_summary.text="동아리 소개"
            holder.detail3_title_target.text="활동지역 및 시간"
            holder.detail3_title_term.text="지원자격"
            holder.detail4_title_summary.text="동아리 소개"
            holder.detail4_title_target.text="활동지역 및 시간"
            holder.detail4_title_term.text="지원자격"

            holder.detail1_text_summary.text=poster.outline
            holder.detail1_text_target.text=poster.period
            holder.detail1_text_term.text=poster.target
            holder.detail2_text_summary.text=poster.outline
            holder.detail2_text_target.text=poster.period
            holder.detail2_text_term.text=poster.target
            holder.detail3_text_summary.text=poster.outline
            holder.detail3_text_target.text=poster.period
            holder.detail3_text_term.text=poster.target
            holder.detail4_text_summary.text=poster.outline
            holder.detail4_text_target.text=poster.period
            holder.detail4_text_term.text=poster.target
        }
        else if(categoryIdx==3){//채용공고
            holder.detail1_title_summary.text="일정"
            holder.detail1_title_target.text="지원자격"
            holder.detail1_title_term.text="근무조건"
            holder.detail2_title_summary.text="일정"
            holder.detail2_title_target.text="지원자격"
            holder.detail2_title_term.text="근무조건"
            holder.detail3_title_summary.text="일정"
            holder.detail3_title_target.text="지원자격"
            holder.detail3_title_term.text="근무조건"
            holder.detail4_title_summary.text="일정"
            holder.detail4_title_target.text="지원자격"
            holder.detail4_title_term.text="근무조건"

            holder.detail1_text_summary.text=poster.period
            holder.detail1_text_target.text=poster.target
            holder.detail1_text_term.text=poster.outline
            holder.detail2_text_summary.text=poster.period
            holder.detail2_text_target.text=poster.target
            holder.detail2_text_term.text=poster.outline
            holder.detail3_text_summary.text=poster.period
            holder.detail3_text_target.text=poster.target
            holder.detail3_text_term.text=poster.period
            holder.detail4_text_summary.text=poster.period
            holder.detail4_text_target.text=poster.target
            holder.detail4_text_term.text=poster.outline
        }
        else if(categoryIdx==4){//학사일정

        }
        else{//categoryIdx==5 //기타

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        val name: TextView = view.findViewById(R.id.item_name)
//        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
        //for card content
//        var tmpcontent1 : TextView =view.findViewById(R.id.tmp_text_1)
//        var tmpcontent2 : TextView =view.findViewById(R.id.tmp_text_2)
//        var cardContentBackground : ImageView =view.findViewById((R.id.IV_card_content))
//        var viewForCardWidth : FrameLayout? = view.findViewById(R.posterIdx.FL_for_width)

        //Poster_detail_1
        var detail1_title :TextView =view.findViewById(R.id.main_detail1_title)
        var detail1_hash : TextView=view.findViewById(R.id.main_detail1_hash)
        var detail1_rectangle : ImageView=view.findViewById(R.id.main_sub_rectangle)
        var detail1_process_paper : ImageView=view.findViewById(R.id.process_paper_1)
        var detail1_step1 : TextView =view.findViewById(R.id.text_main_detail1_step1)
        var detail1_step1_date : TextView =view.findViewById(R.id.text_main_detail1_step1_date)
        var detail1_title_summary : TextView =view.findViewById(R.id.title_main_detail1_summary)
        var detail1_text_summary : TextView =view.findViewById(R.id.text_main_detail1_summary)
        var detail1_title_target : TextView =view.findViewById(R.id.title_main_detail1_target)
        var detail1_text_target : TextView=view.findViewById(R.id.text_main_detail1_target)
        var detail1_title_term :TextView=view.findViewById(R.id.title_main_detail1_term)
        var detail1_text_term :TextView=view.findViewById(R.id.text_main_detail1_term)
        var detail1_title_benefit :TextView=view.findViewById(R.id.title_main_detail1_benefit)
        var detail1_text_benefit :TextView=view.findViewById(R.id.text_main_detail1_benefit)

        //Poster_detail_2
        var detail2_title :TextView =view.findViewById(R.id.main_detail2_title)
        var detail2_hash : TextView=view.findViewById(R.id.main_detail2_hash)
        var detail2_rectangle : ImageView=view.findViewById(R.id.main_sub_rectangle)
        var detail2_process_paper : ImageView=view.findViewById(R.id.process_paper_2)
        var detail2_process_line : ImageView=view.findViewById(R.id.process_line_2)
        var detail2_process_final : ImageView=view.findViewById(R.id.process_final_2)
        var detail2_step1 : TextView =view.findViewById(R.id.text_main_detail2_step1)
        var detail2_step1_date : TextView =view.findViewById(R.id.text_main_detail2_step1_date)
        var detail2_step2 : TextView =view.findViewById(R.id.text_main_detail2_step2)
        var detail2_step2_date : TextView =view.findViewById(R.id.text_main_detail2_step2_date)
        var detail2_title_summary : TextView =view.findViewById(R.id.title_main_detail2_summary)
        var detail2_text_summary : TextView =view.findViewById(R.id.text_main_detail2_summary)
        var detail2_title_target : TextView =view.findViewById(R.id.title_main_detail2_target)
        var detail2_text_target : TextView=view.findViewById(R.id.text_main_detail2_target)
        var detail2_title_term :TextView=view.findViewById(R.id.title_main_detail2_term)
        var detail2_text_term :TextView=view.findViewById(R.id.text_main_detail2_term)
        var detail2_title_benefit :TextView=view.findViewById(R.id.title_main_detail2_benefit)
        var detail2_text_benefit :TextView=view.findViewById(R.id.text_main_detail2_benefit)

        //Poster_detail_3
        var detail3_title :TextView =view.findViewById(R.id.main_detail3_title)
        var detail3_hash : TextView=view.findViewById(R.id.main_detail3_hash)
        var detail3_rectangle : ImageView=view.findViewById(R.id.main_sub_rectangle)
        var detail3_process_paper : ImageView=view.findViewById(R.id.process_paper_3)
        var detail3_process_line : ImageView=view.findViewById(R.id.process_line_3)
        var detail3_process_interview : ImageView=view.findViewById(R.id.process_interview_3)
        var detail3_process_final : ImageView=view.findViewById(R.id.process_final_3)
        var detail3_step1 : TextView =view.findViewById(R.id.text_main_detail3_step1)
        var detail3_step1_date : TextView =view.findViewById(R.id.text_main_detail3_step1_date)
        var detail3_step2 : TextView =view.findViewById(R.id.text_main_detail3_step2)
        var detail3_step2_date : TextView =view.findViewById(R.id.text_main_detail3_step2_date)
        var detail3_step3 : TextView =view.findViewById(R.id.text_main_detail3_step3)
        var detail3_step3_date : TextView =view.findViewById(R.id.text_main_detail3_step3_date)
        var detail3_title_summary : TextView =view.findViewById(R.id.title_main_detail3_summary)
        var detail3_text_summary : TextView =view.findViewById(R.id.text_main_detail3_summary)
        var detail3_title_target : TextView =view.findViewById(R.id.title_main_detail3_target)
        var detail3_text_target : TextView=view.findViewById(R.id.text_main_detail3_target)
        var detail3_title_term :TextView=view.findViewById(R.id.title_main_detail3_term)
        var detail3_text_term :TextView=view.findViewById(R.id.text_main_detail3_term)
        var detail3_title_benefit :TextView=view.findViewById(R.id.title_main_detail3_benefit)
        var detail3_text_benefit :TextView=view.findViewById(R.id.text_main_detail3_benefit)

        //Poster_detail_4
        var detail4_title :TextView =view.findViewById(R.id.main_detail4_title)
        var detail4_hash : TextView=view.findViewById(R.id.main_detail4_hash)
        var detail4_rectangle : ImageView=view.findViewById(R.id.main_sub_rectangle)
        var detail4_process_paper : ImageView=view.findViewById(R.id.process_paper_4)
        var detail4_process_line : ImageView=view.findViewById(R.id.process_line_4)
        var detail4_process_bell : ImageView=view.findViewById(R.id.process_bell)
        var detail4_process_interview : ImageView=view.findViewById(R.id.process_interview_4)
        var detail4_process_final : ImageView=view.findViewById(R.id.process_final_4)
        var detail4_step1 : TextView =view.findViewById(R.id.text_main_detail4_step1)
        var detail4_step1_date : TextView =view.findViewById(R.id.text_main_detail4_step1_date)
        var detail4_step2 : TextView =view.findViewById(R.id.text_main_detail4_step2)
        var detail4_step2_date : TextView =view.findViewById(R.id.text_main_detail4_step2_date)
        var detail4_step3 : TextView =view.findViewById(R.id.text_main_detail4_step3)
        var detail4_step3_date : TextView =view.findViewById(R.id.text_main_detail4_step3_date)
        var detail4_step4 : TextView =view.findViewById(R.id.text_main_detail4_step4)
        var detail4_step4_date : TextView =view.findViewById(R.id.text_main_detail4_step4_date)
        var detail4_title_summary : TextView =view.findViewById(R.id.title_main_detail4_summary)
        var detail4_text_summary : TextView =view.findViewById(R.id.text_main_detail4_summary)
        var detail4_title_target : TextView =view.findViewById(R.id.title_main_detail4_target)
        var detail4_text_target : TextView=view.findViewById(R.id.text_main_detail4_target)
        var detail4_title_term :TextView=view.findViewById(R.id.title_main_detail4_term)
        var detail4_text_term :TextView=view.findViewById(R.id.text_main_detail4_term)
        var detail4_title_benefit :TextView=view.findViewById(R.id.title_main_detail4_benefit)
        var detail4_text_benefit :TextView=view.findViewById(R.id.text_main_detail4_benefit)

    }
}