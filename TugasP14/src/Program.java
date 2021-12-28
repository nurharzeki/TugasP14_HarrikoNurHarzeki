import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Scanner;

import java.sql.*;
public class Program {
    static Connection conn;
    public static void main(String[] args) throws Exception {
        boolean isLanjutkan = true;
        String pil;
        try (Scanner input = new Scanner(System.in)) {
            String url = "jdbc:mysql://localhost:3306/terima_gaji";

            try {
            	Date datenow = new Date();
                SimpleDateFormat tgl = new SimpleDateFormat("E, dd-MM-yyyy");
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss zzz");
            
   
                    System.out.println("Tanggal \t: " + tgl.format(datenow));
                    System.out.println("Waktu \t\t: " + time.format(datenow));
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(url,"root","");
                    System.out.println("Class Driver ditemukan!!!");
                    Gaji gaji = new Gaji();
                    
                    while (isLanjutkan) {
                        System.out.println("----------------------");
                        System.out.println("Database Pegawai");
                        System.out.println("----------------------");
                        System.out.println("1. Lihat Data Pegawai");
                        System.out.println("2. Tambah Data Pegawai");
                        System.out.println("3. Ubah Data Pegawai");
                        System.out.println("4. Hapus Data Pegawai");
                        System.out.println("5. Cari Data Pegawai");
                        
                        System.out.print("\nPilihan anda (1/2/3/4/5): ");
                        pil = input.next();
                        
                        switch (pil) {
                        case "1":
                            gaji.view();
                            break;
                        case "2":
                            gaji.insert();
                            break;
                        case "3":
                            gaji.update();
                            break;
                        case "4":
                            gaji.delete();
                            break;
                        case "5":
                            gaji.search();
                            break;
                        default:
                            System.err.println("\nInput anda tidak ditemukan\nSilakan pilih [1-5]");
                        }
                        
                        System.out.print("\nApakah Anda ingin melanjutkan [y/n]? ");
                        pil = input.next();
                        isLanjutkan = pil.equalsIgnoreCase("y");
   
                    }
                    System.out.println("Program Selesai");
                    
                }
                catch(ClassNotFoundException ex) {
                    System.err.println("Driver Error");
                    System.exit(0);
                }
                catch(SQLException e){
                    System.err.println("Tidak berhasil koneksi");
                }
        }


    }
}
