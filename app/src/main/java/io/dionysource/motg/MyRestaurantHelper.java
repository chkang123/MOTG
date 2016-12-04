package io.dionysource.motg;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by TAEWOONG on 2016. 12. 4..
 */

public class MyRestaurantHelper extends SQLiteOpenHelper {

    private static String TAG = "MyMenuHelper"; //Logcat에 출력할 태그이름
    //디바이스 장치에서 데이터베이스의 경로
    private static String DB_PATH = "";
    private static String DB_NAME ="menu.db"; // 데이터베이스 이름
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    //생성자로 DB 이름과 버전을 넘겨 받는다. 기본 cursor일때 factory=null
    public MyRestaurantHelper(Context context){
        super(context, DB_NAME, null, 1);// 1은 데이터베이스 버젼
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    //onCreate() 함수는 생성자에서 넘겨받은 이름과 버전의 데이터베이스가 존재하지 않을때 한번 호출, 새로운 데이터베이스를 생성할때 사용하기에 알맞음
    @Override
    public void onCreate(SQLiteDatabase database){
        String sql = "CREATE TABLE restaurant (" + 
        "_id INTEGER PRIMARY KEY AUTOINCREMENT," + 
        "name TEXT, "+ 
        "intro TEXT, "+ 
        "tel TEXT, "+ 
        "time TEXT, "+ 
        "pro TEXT, "+ 
        "area TEXT, "+ 
        "address TEXT, "+ 
        "location TEXT, "+ 
        "code TEXT);";

        database.execSQL(sql);
    }

    //데이터베이스가 존재하지만 버전이 다르면 호출, 데이터베이스를 변경하고 싶을때 버전을 올려주고 새로운 작업
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS word");
        onCreate(db);
    }

    public void createDataBase() throws IOException
    {
        //데이터베이스가 없으면 asset폴더에서 복사해온다.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    ///data/data/your package/databases/Da Name <-이 경로에서 데이터베이스가 존재하는지 확인한다
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //assets폴더에서 데이터베이스를 복사한다.
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //데이터베이스를 열어서 쿼리를 쓸수있게만든다.
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }
}
