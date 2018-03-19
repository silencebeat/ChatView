package silencebeat.com.chatview.Modules

import silencebeat.com.chatview.Entities.Models.Comment

/**
 * Created by Candra Triyadi on 27/02/2018.
 */


interface OnItemCommentClickListener{
    fun onItemCommentClicked(comment: Comment)
}

interface AudioRecordingListener{
    fun onFinishRecording(filePath: String)
}
