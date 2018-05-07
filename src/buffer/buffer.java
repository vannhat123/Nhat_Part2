/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buffer;

import java.net.*;
import java.io.*;

/**
 *
 * @author nhat
 */
public class buffer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try {
            // tạo 1 url địa chỉ là https://vnexpress.net.
            // kết nối tới web đó
            // sử dụng stringbuilder nối chuỗi sau khi lấy dữ liệu về.
            // InputStreamReader(conn.getInputStream) lấy dữ liệu ra. sử dụng BufferedReader để dọc dạng char.
            URL url = new URL("https://vnexpress.net");
            URLConnection conn = url.openConnection();
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));) {
                String line;
                while ((line = br.readLine()) != null) {
                    contentBuilder.append(line);
                    contentBuilder.append("\n");
                }
            }
            
            // tạo 1 folder saved_pages
            String directoryName = "saved_pages";
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }
            // tạo ra 1 trường listOFFiles tính ra số lượng file lưu ra. đặt theo thứ tự tăng dần.
            File[] listOfFiles = directory.listFiles();
            int count = 0;
            for (File f : listOfFiles) {
                String strCount = f.getName().replace(".html", "").replace("index", "");
                try {
                    // ép kiểu Integer cho strCount
                    int currentNumber = Integer.parseInt(strCount);
                    // nếu currentNumber >0 thì cho count = currentNumber.
                    if (currentNumber > count) {
                        count = currentNumber;
                    }
                } catch (NumberFormatException e) {
                }
            }
            // tạo ra file html theo thứ tự từ 1 nếu có.
            String fileName = "index" + (count + 1) + ".html";
            File file = new File(directoryName + "/" + fileName);
            // viết file fw vào. sau đó dùng bufferredWriter viết lên tên file.
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            // thêm tất cả text của trang html đó vào file html vừa tạo.
            bw.write(contentBuilder.toString());
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
