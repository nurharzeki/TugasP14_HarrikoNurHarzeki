import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

public class Gaji implements PTABC {
	//static Scanner scanner;
	static Connection conn;
	String url = "jdbc:mysql://localhost:3306/terima_gaji";
	
	public Integer noPegawai;
	public Integer gajiPokok = 0; 
    public Integer jumlahHariMasuk = 0;
    public Integer potongan = 0;
    public Integer jumlahHariAbsen = 0;
    public Integer totalGaji = 0;
    public String namaPegawai;
    public String jabatan;


    Scanner Input = new Scanner (System.in);

    public void view() throws SQLException {
		String a = "\nData Seluruh Pegawai";
		System.out.println(a.toUpperCase());
						
		String sql ="SELECT * FROM karyawan";
		conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
			System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("id_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("gaji_total"));
            System.out.print("\n");
		}
	}
    
    public void insert() throws SQLException {
    	String b = "\nTambah Data Pegawai";
		System.out.println(b.toUpperCase());
		
    	try {
	        // NoPegawai
	    	System.out.print("Masukkan nomor pegawai: ");
	        noPegawai = Input.nextInt();
	        Input.nextLine();
	
	        // NamaPegawai
	        System.out.print("\nMasukkan nama pegawai: ");
	        namaPegawai = Input.nextLine(); 
		        
	        // PilihJabatan
	        int pilihJabatan;
	        System.out.println("\n1. Direktur\n2. Manajer\n3. Staff \n4. Staff Assistant \n5. Karyawan lainnya");
	        System.out.print("Pilih jabatan (1 - 5) : ");
	        pilihJabatan = Input.nextInt();
	        if (pilihJabatan == 1){
	            jabatan = "Direktur";
	        }
	        else if (pilihJabatan == 2){
	            jabatan = "Manajer";
	        }
	        else if (pilihJabatan == 3){
	            jabatan = "Staff";
	        }
	        else if (pilihJabatan == 4){
	            jabatan = "Staff Assistant";
	        }
            else if (pilihJabatan == 5){
                jabatan = "Karyawan lainnya";
            }
	        else{
	            System.out.println("Jabatan tidak tersedia");
	        }
	        System.out.println(jabatan.toUpperCase());
	
	        if (jabatan == "Direktur"){
	            gajiPokok = 50000000;
	        }
	        else if (jabatan == "Manajer"){
	            gajiPokok = 20000000;
	        }
	        else if (jabatan == "Staff"){
	            gajiPokok = 10000000;
	        }
	        else if (jabatan == "Staff Assistant"){
	            gajiPokok = 5000000;
	        }
            else if (jabatan == "Karyawan lainnya"){
                gajiPokok = 2500000;
            }
	        else{
	            System.out.println("\nGaji pokok tidak tersedia");
	        }
	        System.out.println("\nGaji pokok : " + gajiPokok);
	        
	        // JumlahHariMasuk
	        System.out.print("\nMasukkan jumlah hari masuk (0 - 30) : ");
	        jumlahHariMasuk = Input.nextInt();
	        jumlahHariAbsen = 30 - jumlahHariMasuk;
	        System.out.println("Jumlah hari masuk : " + jumlahHariMasuk);
	        
	        // Potongan
	        potongan = jumlahHariAbsen*100000;
	        System.out.println("\nPotongan : " + potongan);
	        
	        // TotalGaji
	        totalGaji = gajiPokok - potongan;
	        System.out.println("Total gaji : " + totalGaji + "\n");
	        
	        String sql = "INSERT INTO karyawan (id_pegawai, nama, jabatan, hari_masuk, gaji_total) VALUES ('"+noPegawai+"','"+namaPegawai+"','"+jabatan+"','"+jumlahHariMasuk+"','"+totalGaji+"')";
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        statement.execute(sql);
	        System.out.println("Berhasil input data!!");
    	}
    	catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } 
    	catch (InputMismatchException e) {
	    	System.err.println("Inputan harus berupa angka");
	   	}
	} 

	public void update() throws SQLException{
		String c = "\nUbah Data Pegawai";
		System.out.println(c.toUpperCase());
		
		try {
            view();
            System.out.print("\nMasukkan Nomor Pegawai yang akan di ubah : ");
            Integer noPegawai = Integer.parseInt(Input.nextLine());
            
            String sql = "SELECT * FROM karyawan WHERE id_pegawai = " +noPegawai;
            conn = DriverManager.getConnection(url,"root","");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                
                System.out.print("Nama baru ["+result.getString("nama_pegawai")+"]\t: ");
                String namaPegawai = Input.nextLine();
                   
                sql = "UPDATE karyawan SET nama='"+namaPegawai+"' WHERE id_pegawai='"+noPegawai+"'";
                //System.out.println(sql);

                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                }
            }
            statement.close();        
        } 
		catch (SQLException e) {
        	System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
	}
	
	public void delete() {
		String d = "\nHapus Data Pegawai";
		System.out.println(d.toUpperCase());
		
		try{
	        view();
	        System.out.print("\nMasukan ID Pegawai yang akan Anda Hapus : ");
	        Integer noPegawai= Integer.parseInt(Input.nextLine());
	        
	        String sql = "DELETE FROM karyawan WHERE id_pegawai = "+ noPegawai;
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai (Nomor "+noPegawai+")");
	        }
	   }
		catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data");
	    }
        catch(Exception e){
            System.out.println("masukan data yang benar");
        }
	}
	
	public void search() throws SQLException {
		String text5 = "\nCari Data Pegawai";
		System.out.println(text5.toUpperCase());
				
		System.out.print("Masukkan Nama Pegawai yang ingin dilihat : ");    
		String keyword = Input.nextLine();
		
		String sql = "SELECT * FROM karyawan WHERE nama LIKE '%"+keyword+"%'";
		conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 
                
        while(result.next()){
        	System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("id_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("gaji_total"));
            System.out.print("\n");
        }
	}  
}