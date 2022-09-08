package alba;

public class commentDTO {

	int commentNo;
	int no;
	int category;
	String writer;
	String content;
	String writeDate;

	public commentDTO() {}
	
	public commentDTO(int commentNo, int no, int category, String writer, String content, String writeDate) {
		this.commentNo=commentNo;
		this.no=no;
		this.category=category;
		this.writer=writer;
		this.writeDate=writeDate;
		this.content=content;
		// TODO Auto-generated constructor stub
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
}
