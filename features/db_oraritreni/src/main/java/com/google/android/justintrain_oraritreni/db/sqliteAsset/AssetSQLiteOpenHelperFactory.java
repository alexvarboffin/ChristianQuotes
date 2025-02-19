package com.google.android.justintrain_oraritreni.db.sqliteAsset;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


/**
 * Implements {@link SupportSQLiteOpenHelper.Factory} using the SQLite implementation in the
 * framework.
 */
@SuppressWarnings("unused")
public class AssetSQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {

    private final int versionName;

    public AssetSQLiteOpenHelperFactory(int versionName) {//alex custom code...
        this.versionName = versionName;
    }

    @NonNull
    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new AssetSQLiteOpenHelper(
                configuration.context, configuration.name, null,
                /*configuration.version*/ /*Constants.version*/versionName,
                /*configuration.errorHandler*/null,
                configuration.callback
        );
    }
}
