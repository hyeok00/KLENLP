package alba;

public class boradDTO {
   int no; // 게시글 번호
   String title; // 게시글 제목
   String writer; // 작성자
   String writedate; // 작성일
   int count; //조회수
   
   public boradDTO(int no, String title, String writer, String writedate, int count) {
      this.no = no;
      this.title = title;
      this.writer = writer;
      this.writedate = writedate;
      this.count = count;
   }
   public int getNo() {
      return no;
   }
   public void setNo(int no) {
      this.no = no;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getWriter() {
      return writer;
   }
   public void setWriter(String writer) {
      this.writer = writer;
   }
   public String getWritedate() {
      return writedate;
   }
   public void setWritedate(String writedate) {
      this.writedate = writedate;
   }
   public int getCount() {
      return count;
   }
   public void setCount(int count) {
      this.count = count;
   }
   
   
   
}