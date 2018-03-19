package silencebeat.com.chatview.Entities.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Candra Triyadi on 15/03/2018.
 */

public class Comment {

    @SerializedName("noteId")
    @Expose
    private String noteId;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("leaseId")
    @Expose
    private String leaseId;
    @SerializedName("propertyId")
    @Expose
    private String propertyId;
    @SerializedName("type_note")
    @Expose
    private String typeNote;
    @SerializedName("adminId")
    @Expose
    private String adminId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("noteText")
    @Expose
    private String noteText;
    @SerializedName("noteDate")
    @Expose
    private String noteDate;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("fileUrl")
    @Expose
    private String fileUrl;
    @SerializedName("thumbnail_video")
    @Expose
    private String thumbnailVideo;
    @SerializedName("adminName")
    @Expose
    private String adminName;

    public Comment(){

    }

    public Comment(String adminId, String noteText, String noteDate, String fileUrl, String thumbnailVideo){
        this.adminId = adminId;
        this.noteText = noteText;
        this.noteDate = noteDate;
        this.fileUrl = fileUrl;
        this.thumbnailVideo = thumbnailVideo;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getTypeNote() {
        return typeNote;
    }

    public void setTypeNote(String typeNote) {
        this.typeNote = typeNote;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbnailVideo() {
        return thumbnailVideo;
    }

    public void setThumbnailVideo(String thumbnailVideo) {
        this.thumbnailVideo = thumbnailVideo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

}
