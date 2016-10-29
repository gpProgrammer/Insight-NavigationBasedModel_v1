//package com.reserch2016.sliit.insight_navigationbasedmodel;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
///**
// * Created by Ganindu Ranasinghe on 05-Jun-16.
// */
//public class JDBCConnection extends AsyncTask<Void, Void, Connection> {
//
//    // Tag for JDBCConnection Class
//    private static final String TAG = "JDBCConnection";
//    Connection connection = null;
//
////    public Connection getCConnection()
////    {
////
////    }
//
//    public void closeConnection()
//    {
//        try {
//            connection.close();
//            Log.i(TAG, "Connection Close Successfully!");
//        } catch (SQLException e) {
//            Log.e(TAG, "Connection Close Error!");
//        }
//    }
//
//
//    //@TargetApi(Build.VERSION_CODES.KITKAT)
//    @Override
//    protected Connection doInBackground(Void... params) {
//        try {
//            Class<?> forName = Class.forName("org.postgresql.Driver");
//
////            PGSimpleDataSource ds = new PGSimpleDataSource();
////
////            ds.setServerName("127.0.0.1:5434");
////            ds.setSsl(true);
////            ds.setUser("postgres");
////            ds.setDatabaseName("new");
//////            ds.setPassword("123456");
////            ds.setSslfactory(DumperFactory.class.getName());
////
////            connection = DriverManager.getConnection(
////                    "jdbc:postgresql://127.0.0.1:5434/new", "postgres",
////                    "123456");
//            //connection = ds.getConnection();
//
//            String url = "jdbc:postgresql://localhost:5434/new";
//            Properties props = new Properties();
//            props.setProperty("user","postgres");
//            props.setProperty("password","123456");
//            props.setProperty("ssl","true");
//            connection = DriverManager.getConnection(url, props);
//
//
//        } catch (ClassNotFoundException e) {
//            Log.e(TAG, "Connection Class was not found!");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            Log.e(TAG, "Connection Error!");
//            e.printStackTrace();
//        }
//        if (connection != null) {
//            Log.i(TAG, "You made it, take control your database now!");
//        } else {
//            Log.i(TAG,"Failed to make connection!");
//        }
//
////        return connection;
//        return connection;
//    }
//
////    public static class DumperFactory extends WrappedFactory {
////        public DumperFactory(String arg) throws GeneralSecurityException {
////            SSLContext ctx = SSLContext.getInstance("TLS");
////            ctx.init(null, new TrustManager[] { new DumperTM() }, null);
////            _factory = ctx.getSocketFactory();
////        }
////    }
////
////    public static class DumperTM implements X509TrustManager {
////        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
////        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
////        public void checkServerTrusted(X509Certificate[] certs, String authType) {
////            for (int i=0; i<certs.length; ++i) {
////                System.out.println("Cert " + (i+1) + ":");
////                System.out.println("    Subject: " + certs[i].getSubjectX500Principal().getName());
////                System.out.println("    Issuer: " + certs[i].getSubjectX500Principal().getName());
////            }
////        }
////    }
//}
