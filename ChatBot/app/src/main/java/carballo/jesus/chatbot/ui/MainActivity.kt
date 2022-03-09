package carballo.jesus.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import carballo.jesus.chatbot.R
import carballo.jesus.chatbot.data.Message
import carballo.jesus.chatbot.utils.Constans.OPEN_SEARCH
import carballo.jesus.chatbot.utils.Constans.OPEN_GOOGLE
import carballo.jesus.chatbot.utils.Constans.SEND_ID
import carballo.jesus.chatbot.utils.Constans.RECEIVE_ID
import carballo.jesus.chatbot.utils.Time
import kotlinx.coroutines.*
import carballo.jesus.chatbot.utils.BotResponse


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var messageList = mutableListOf<Message>()
    private lateinit var adapter:MessangingAdapter
    private val botList = listOf("Peter","Francesca","Luigi","Igor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents(){
        btn_send.setOnClickListener{
            sendMessage()
        }
        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }
    private fun recyclerView(){
        adapter = MessangingAdapter()
        rv_messages.adapter=adapter
        rv_messages.layoutManager= LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()){
            messageList.add(Message(message, SEND_ID,timeStamp))
            et_message.setText("")
            adapter.insertMessage(Message(message, SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)
            botResponse(message)
        }
    }
    private fun botResponse(message:String){
        val timeStamp= Time.timeStamp()
        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main){
                val response= BotResponse.basicResponse(message)
                messageList.add(Message(response, RECEIVE_ID,timeStamp))

                adapter.insertMessage(Message(response, RECEIVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

                when(response){
                    OPEN_GOOGLE->{
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data= Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data= Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }
    private fun customBotMessage(message:String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                messageList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
}