package com.yung_coder.oluwole.tk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList

class About : MaterialAboutActivity() {
    override fun getMaterialAboutList(p0: Context): MaterialAboutList {
        val version_name = BuildConfig.VERSION_NAME

        val card_builder = MaterialAboutCard.Builder()

        card_builder.addItem(MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name).toUpperCase())
                .icon(R.drawable.logo)
                .desc(getString(R.string.app_desc, "©"))
                .build())
        card_builder.addItem(MaterialAboutActionItem.Builder()
                .text("Version")
                .subText(version_name)
                .icon(R.drawable.ic_info_outline_24dp)
                .build())

        card_builder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.developer_name))
                .subText(getString(R.string.developer_alias, "@"))
                .icon(R.drawable.ic_person_24dp)
                .build())

        card_builder.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.github_text))
                .icon(R.drawable.ic_github_circle)
                .setOnClickAction {
                    val url = Uri.parse("https://github.com/MOluwole/Akeko_Mobile")
                    val intent = Intent(Intent.ACTION_VIEW, url)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                .build())

        val authorCard = MaterialAboutCard.Builder()
        authorCard.title("Developer")

        authorCard.addItem(MaterialAboutActionItem.Builder()
                .text(getString(R.string.developer_name))
                .subText(getString(R.string.developer_alias, "@"))
                .icon(R.drawable.ic_twitter)
                .setOnClickAction {
                    val url = Uri.parse("https://twitter.com/MOluwole")
                    val intent = Intent(Intent.ACTION_VIEW, url)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                .build())

        return MaterialAboutList(card_builder.build(), authorCard.build())

    }

    override fun getActivityTitle(): CharSequence? {
        Log.e("ClassName", this.localClassName)
        return this.localClassName
    }
}
