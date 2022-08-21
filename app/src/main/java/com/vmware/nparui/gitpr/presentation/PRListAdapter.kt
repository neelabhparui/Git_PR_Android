package com.vmware.nparui.gitpr.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vmware.nparui.gitpr.R
import com.vmware.nparui.gitpr.data.entities.PullRequestInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PRListAdapter(private val prList : ArrayList<PullRequestInfo>) : RecyclerView.Adapter<PRListAdapter.PRViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun update(list : List<PullRequestInfo>) {
        prList.clear()
        prList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PRViewHolder(parent.context, LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false))

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        holder.bind(prList[position])
    }

    override fun getItemCount() = prList.size

    class PRViewHolder(private val context : Context, view : View) : RecyclerView.ViewHolder(view) {
        private val avatar = view.findViewById<AppCompatImageView>(R.id.avatar_img)
        private val title = view.findViewById<AppCompatTextView>(R.id.pr_title)
        private val desc = view.findViewById<AppCompatTextView>(R.id.pr_desc)
        private val branch = view.findViewById<AppCompatTextView>(R.id.pr_branch_info)
        private val created = view.findViewById<AppCompatTextView>(R.id.pr_created_at)
        private val closed = view.findViewById<AppCompatTextView>(R.id.pr_closed_at)

        fun bind(pullRequestInfo: PullRequestInfo) {
            Log.println(Log.INFO, "ViewHolder", "binding")
            Picasso.get().load(pullRequestInfo.user.avatarUrl).into(avatar)
            title.text = pullRequestInfo.title
            desc.text = pullRequestInfo.user.name
            branch.text = String.format(context.getString(R.string.branch), pullRequestInfo.head.ref, pullRequestInfo.base.ref)
            val createdDatetime: LocalDateTime =
                LocalDateTime.parse(pullRequestInfo.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
            val closedDatetime =
                LocalDateTime.parse(pullRequestInfo.closedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
            created.text = createdDatetime.format(DateTimeFormatter.ofPattern("HH:mm '@' dd-MM-yy"))
            closed.text = closedDatetime.format(DateTimeFormatter.ofPattern("HH:mm '@' dd-MM-yy"))
        }
    }
}