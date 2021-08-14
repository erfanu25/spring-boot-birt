package dev.model;

public class SelectionCriteria {

	private String userIdFrom;
	private String userIdTo;
	private String dobFrom;
	private String dobTo;
	private String reportFormat;

	public String getUserIdFrom() {
		return userIdFrom;
	}

	public void setUserIdFrom(String userIdFrom) {
		this.userIdFrom = userIdFrom;
	}

	public String getUserIdTo() {
		return userIdTo;
	}

	public void setUserIdTo(String userIdTo) {
		this.userIdTo = userIdTo;
	}

	public String getDobFrom() {
		return dobFrom;
	}

	public void setDobFrom(String dobFrom) {
		this.dobFrom = dobFrom;
	}

	public String getDobTo() {
		return dobTo;
	}

	public void setDobTo(String dobTo) {
		this.dobTo = dobTo;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	@Override
	public String toString() {
		return "User [userIdFrom=" + userIdFrom + ", userIdTo=" + userIdTo + ", dobFrom=" + dobFrom + ", dobTo=" + dobTo
				+ ", reportFormat=" + reportFormat + "]";
	}

}
