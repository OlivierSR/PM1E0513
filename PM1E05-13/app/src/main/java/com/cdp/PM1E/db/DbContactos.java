package com.cdp.PM1E.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cdp.PM1E.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbAyuda {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String telefono, String descripcion, String pais) {

        long id = 0;

        try {
            DbAyuda dbAyuda = new DbAyuda(context);
            SQLiteDatabase db = dbAyuda.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("descripcion", descripcion);
            values.put("pais", pais);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos() {

        DbAyuda dbAyuda = new DbAyuda(context);
        SQLiteDatabase db = dbAyuda.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setDescripcion(cursorContactos.getString(3));
                contacto.setPais(cursorContactos.getString(4));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id) {

        DbAyuda dbAyuda = new DbAyuda(context);
        SQLiteDatabase db = dbAyuda.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setDescripcion(cursorContactos.getString(3));
            contacto.setPais(cursorContactos.getString(4));

        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String descripcion, String pais) {

        boolean correcto = false;

        DbAyuda dbAyuda = new DbAyuda(context);
        SQLiteDatabase db = dbAyuda.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', descripcion = '" + descripcion + "', pais = '" + pais + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        DbAyuda dbAyuda = new DbAyuda(context);
        SQLiteDatabase db = dbAyuda.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}

//Integrantes://
//Axel Barahona - 202020020005//
//Olivier Jesús Sánchez - 201810050013//