 package org.sz.platform.model.system;
 
 import org.sz.platform.model.system.SysFile;

 import java.util.Date;
 import org.apache.commons.lang.builder.EqualsBuilder;
 import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;
 
 public class SysFile extends BaseModel
 {
   public static Short FILE_DEL = 1;
 
   public static Short FILE_NOT_DEL = 0;
 
   public static String FILE_UPLOAD_UNKNOWN = "unknown";
 
   public static String FILETYPE_OTHERS = "others";
   protected Long fileId;
   protected Long typeId;
   protected String fileName;
   protected String filePath;
   protected Date createtime;
   protected String ext;
   protected String fileType;
   protected String note;
   protected Long creatorId;
   protected String creator;
   protected Long totalBytes;
   protected Short delFlag;
   protected String typeName;
 
   public String getTypeName()
   {
     return this.typeName;
   }
   public void setTypeName(String typeName) {
     this.typeName = typeName;
   }
   public void setFileId(Long fileId) {
     this.fileId = fileId;
   }
 
   public Long getFileId()
   {
     return this.fileId;
   }
 
   public void setTypeId(Long typeId) {
     this.typeId = typeId;
   }
 
   public Long getTypeId()
   {
     return this.typeId;
   }
 
   public void setFileName(String fileName) {
     this.fileName = fileName;
   }
 
   public String getFileName()
   {
     return this.fileName;
   }
 
   public void setFilePath(String filePath) {
     this.filePath = filePath;
   }
 
   public String getFilePath()
   {
     return this.filePath;
   }
 
   public void setCreatetime(Date createtime) {
     this.createtime = createtime;
   }
 
   public Date getCreatetime()
   {
     return this.createtime;
   }
 
   public void setExt(String ext) {
     this.ext = ext;
   }
 
   public String getExt()
   {
     return this.ext;
   }
 
   public void setFileType(String fileType) {
     this.fileType = fileType;
   }
 
   public String getFileType()
   {
     return this.fileType;
   }
 
   public void setNote(String note) {
     this.note = note;
   }
 
   public String getNote()
   {
     return this.note;
   }
 
   public void setCreatorId(Long creatorId) {
     this.creatorId = creatorId;
   }
 
   public Long getCreatorId()
   {
     return this.creatorId;
   }
 
   public void setCreator(String creator) {
     this.creator = creator;
   }
 
   public String getCreator()
   {
     return this.creator;
   }
 
   public void setTotalBytes(Long totalBytes) {
     this.totalBytes = totalBytes;
   }
 
   public Long getTotalBytes()
   {
     return this.totalBytes;
   }
 
   public void setDelFlag(Short delFlag) {
     this.delFlag = delFlag;
   }
 
   public Short getDelFlag()
   {
     return this.delFlag;
   }
 
   public boolean equals(Object object)
   {
     if (!(object instanceof SysFile)) {
       return false;
     }
     SysFile rhs = (SysFile)object;
     return new EqualsBuilder().append(this.fileId, rhs.fileId).append(this.typeId, rhs.typeId).append(this.fileName, rhs.fileName).append(this.filePath, rhs.filePath).append(this.createtime, rhs.createtime).append(this.ext, rhs.ext).append(this.fileType, rhs.fileType).append(this.note, rhs.note).append(this.creatorId, rhs.creatorId).append(this.creator, rhs.creator).append(this.totalBytes, rhs.totalBytes).append(this.delFlag, rhs.delFlag).isEquals();
   }
 
   public int hashCode()
   {
     return new HashCodeBuilder(-82280557, -700257973).append(this.fileId).append(this.typeId).append(this.fileName).append(this.filePath).append(this.createtime).append(this.ext).append(this.fileType).append(this.note).append(this.creatorId).append(this.creator).append(this.totalBytes).append(this.delFlag).toHashCode();
   }
 
   public String toString()
   {
     return new ToStringBuilder(this).append("fileId", this.fileId).append("typeId", this.typeId).append("fileName", this.fileName).append("filePath", this.filePath).append("createtime", this.createtime).append("ext", this.ext).append("fileType", this.fileType).append("note", this.note).append("creatorId", this.creatorId).append("creator", this.creator).append("totalBytes", this.totalBytes).append("delFlag", this.delFlag).toString();
   }
 }

