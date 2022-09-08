package alba;

import java.util.*;

public class articleDTO {

   int no;
   int category;
   String article;
   int goodCount;
   int badCount;

   int size;
   String color;
   String background;
   String align;
   
   String title;
   String writer;
   String writeDate;
   int count;

   public articleDTO() {
   }

   public articleDTO(int no, int category, String article, int goodCount, int badCount) {
      this.no = no;
      this.category = category;
      this.article = article;
      this.goodCount = goodCount;
      this.badCount = badCount;
   }

   
   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public String getBackground() {
      return background;
   }

   public void setBackground(String background) {
      this.background = background;
   }

   public String getAlign() {
      return align;
   }

   public void setAlign(String align) {
      this.align = align;
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

   public String getArticle() {
      return article;
   }

   public void setArticle(String article) {
      this.article = article;
   }

   public int getGoodCount() {
      return goodCount;
   }

   public void setGoodCount(int goodCount) {
      this.goodCount = goodCount;
   }

   public int getBadCount() {
      return badCount;
   }

   public void setBadCount(int badCount) {
      this.badCount = badCount;
   }

   public String getWriter() {
      return writer;
   }

   public void setWriter(String writer) {
      this.writer = writer;
   }

   public String getWriteDate() {
      return writeDate;
   }

   public void setWriteDate(String writeDate) {
      this.writeDate = writeDate;
   }

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }
   
   public void increaseCount() {
      this.count++;
   }
   public void decreaseCount() {
      this.count--;
   }

}