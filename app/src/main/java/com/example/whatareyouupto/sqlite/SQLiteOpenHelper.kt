package com.example.whatareyouupto.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.w3c.dom.Text

// SQLiteOpenHelper 상속받아 SQLite 를 사용하도록 하겠습니다.
class SqliteHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    //onCreate(), onUpgrade() 두가지 메소드를 오버라이드 받아 줍시다.

    //데이터베이스가 만들어 지지않은 상태에서만 작동합니다. 이미 만들어져 있는 상태라면 실행되지 않습니다.
    override fun onCreate(db: SQLiteDatabase?) {
        //테이블을 생성할 쿼리를 작성하여 줍시다.
        val create = "create table memo (id integer primary key,title text,content text, image integer, mintime text, maxtime text,year integer, month integer, day integer)"
        //실행시켜 줍니다.
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //insert 메소드
    fun insertMemo(memo: Memo){
        val values = ContentValues()
        //넘겨줄 컬럼의 매개변수 지정
        values.put("title",memo.title)
        values.put("content",memo.content)
        values.put("image",memo.image)
        values.put("mintime",memo.mintime)
        values.put("maxtime",memo.maxtime)
        values.put("year",memo.year)
        values.put("month",memo.month)
        values.put("day",memo.day)

        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.insert("memo",null,values)
        //사용이 끝나면 반드시 close()를 사용하여 메모리누수 가 되지않도록 합시다.
        wd.close()
    }


    //select 메소드
    fun selectMemo(year : Int,month : Int, day : Int):MutableList<Memo>{
        val list = mutableListOf<Memo>()
        //전체조회
        val selectAll = "select * from memo WHERE year = $year and month = $month and day = $day ORDER BY id DESC"
        //읽기전용 데이터베이스 변수
        val rd = this.readableDatabase
        //데이터를 받아 줍니다.
        val cursor = rd.rawQuery(selectAll,null)

        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
            val image = cursor.getInt(cursor.getColumnIndexOrThrow("image"))
            val mintime = cursor.getString(cursor.getColumnIndexOrThrow("mintime"))
            val maxtime = cursor.getString(cursor.getColumnIndexOrThrow("maxtime"))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
            val month = cursor.getInt(cursor.getColumnIndexOrThrow("month"))
            val day = cursor.getInt(cursor.getColumnIndexOrThrow("day"))



            list.add(Memo(id,title,content,image,mintime,maxtime,year,month,day))
        }
        cursor.close()
        rd.close()

        return list
    }

    //update 메소드
    fun updateMemo(id :Long,title: String,content : String,image : Int, mintime : String, maxtime : String,){
        val values = ContentValues()

        values.put("title",title)
        values.put("content",content)
        values.put("image",image)
        values.put("mintime",mintime)
        values.put("maxtime",maxtime)


        val wd = writableDatabase
        wd.update("memo",values,"id = $id",null)
        wd.close()

    }

    //delete 메소드
    fun deleteMemo(id : Long){
        val delete = "delete from memo where id = $id"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }

    fun deleteAllMemo(){
        val delete = "delete from Memo"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()

    }

}