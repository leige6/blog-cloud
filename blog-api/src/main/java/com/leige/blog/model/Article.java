package com.leige.blog.model;

import com.leige.blog.common.base.model.CommonEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class Article extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 文章Id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 文章标题 */
	@NotBlank
	private String title;

	/** 文章摘要 */
	private String summary;

	/** 文章来源 */
	private String source;

	/** 图标 */
	private String icon;

	/** 类型：文字类、图片类 */
	private Integer type;

	/** 是否允许回复：是，否 0不允许回复 1允许回复 */
	private Integer replyFlag;

	/** 置顶:是，否 */
	private Integer topFlag;

	/** 置顶开始时间 */
	private Date topStart;

	/** 置顶结束时间 */
	private Date topEnd;

	/** 状态：未提交(0)，已提交(1)，通过(2)，未通过(3) */
	private Integer verifyStatus;

	/** 审核意见 */
	private String verifyOpinion;

	/** 审核人id */
	private Long verifyId;

	/** 审核时间 */
	private Date verifyTime;

	/** 发布状态：已发布，未发布 */
	private Integer publishStatus;

	/** 发布时间 */
	private Date publishTime;

	/** 发布人 */
	private Long publishId;

	/** 点赞数量 */
	private Integer likesNumber;

	/** 负赞数量 */
	private Integer hateNumber;

	/** 浏览量 */
	private Integer viewNumber;

	/** 回复量 */
	private Integer replyNumber;

	/** 违规量 */
	private Integer illegalNumber;

	/** 在新增时直接发布为快捷发布(审核状态为已审核)、在新增过后，通过审核(未提交--提交--审核)审核发布为审核发布 */
	private Integer isQuick;

	private Integer seq;
	/** 标签 */
	private String tagId;

	/** 栏目id */
	private Long sectionId;

	/** 内容 */
	@NotBlank
	private String content;

	/********************扩展属性************************/
	/** 标签名称 */
	@Transient
	private String tagNames;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary == null ? null : summary.trim();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(Integer replyFlag) {
		this.replyFlag = replyFlag;
	}

	public Integer getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(Integer topFlag) {
		this.topFlag = topFlag;
	}

	public Date getTopStart() {
		return topStart;
	}

	public void setTopStart(Date topStart) {
		this.topStart = topStart;
	}

	public Date getTopEnd() {
		return topEnd;
	}

	public void setTopEnd(Date topEnd) {
		this.topEnd = topEnd;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyOpinion() {
		return verifyOpinion;
	}

	public void setVerifyOpinion(String verifyOpinion) {
		this.verifyOpinion = verifyOpinion == null ? null : verifyOpinion.trim();
	}

	public Long getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(Long verifyId) {
		this.verifyId = verifyId;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	public Integer getLikesNumber() {
		return likesNumber;
	}

	public void setLikesNumber(Integer likesNumber) {
		this.likesNumber = likesNumber;
	}

	public Integer getHateNumber() {
		return hateNumber;
	}

	public void setHateNumber(Integer hateNumber) {
		this.hateNumber = hateNumber;
	}

	public Integer getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getReplyNumber() {
		return replyNumber;
	}

	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}

	public Integer getIllegalNumber() {
		return illegalNumber;
	}

	public void setIllegalNumber(Integer illegalNumber) {
		this.illegalNumber = illegalNumber;
	}

	public Integer getIsQuick() {
		return isQuick;
	}

	public void setIsQuick(Integer isQuick) {
		this.isQuick = isQuick;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}


}