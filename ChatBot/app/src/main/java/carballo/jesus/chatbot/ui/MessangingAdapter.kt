package carballo.jesus.chatbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import carballo.jesus.chatbot.R
import carballo.jesus.chatbot.data.Message
import carballo.jesus.chatbot.utils.Constans.RECEIVE_ID
import carballo.jesus.chatbot.utils.Constans.SEND_ID
import kotlinx.android.synthetic.main.message_item.*
import kotlinx.android.synthetic.main.message_item.view.*

class MessangingAdapter:RecyclerView.Adapter<MessangingAdapter.MessageViewHolder>() {

    var messageList = mutableListOf<Message>()
    inner class MessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener{

            }
        }
    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int):MessageViewHolder {
       return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false))
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int){
    val currentMessage=messageList[position]
        when (currentMessage.id){
            SEND_ID->{
                holder.itemView.tv_message.apply {
                    text=currentMessage.message
                    visibility=View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility=View.GONE
            }
            RECEIVE_ID->{
                holder.itemView.tv_message.apply{
                    text=currentMessage.message
                    visibility= View.VISIBLE
                }
                holder.itemView.tv_message.visibility=View.GONE
            }
        }
    }
    override fun getItemCount(): Int{
   return messageList.size
    }
    fun insertMessage(message:Message){
        this.messageList.add(message)
        notifyItemChanged(messageList.size)
        notifyDataSetChanged()
    }



}