/*
 * Copyright (c) 2019 Mike Penz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mikepenz.iconics.view

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import com.mikepenz.iconics.Iconics
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.internal.CheckableIconBundle
import com.mikepenz.iconics.internal.IconicsView
import com.mikepenz.iconics.internal.IconicsViewsAttrsApplier

/**
 * @author pa.gulko zTrap (06.07.2017)
 */
open class IconicsCompoundButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : CompoundButton(context, attrs, defStyle), IconicsView {
    private val iconsBundle = CheckableIconBundle()

    var checkedIcon: IconicsDrawable?
        get() = iconsBundle.checkedIcon
        set(icon) {
            iconsBundle.checkedIcon = tryToEnableIconicsAnimation(icon)
            buttonDrawable = iconsBundle.createStates(context)
        }

    var uncheckedIcon: IconicsDrawable?
        get() = iconsBundle.uncheckedIcon
        set(icon) {
            iconsBundle.uncheckedIcon = tryToEnableIconicsAnimation(icon)
            buttonDrawable = iconsBundle.createStates(context)
        }

    init {
        @Suppress("LeakingThis")
        initialize(context, attrs, defStyle)
    }

    @RestrictTo(LIBRARY_GROUP)
    override fun initialize(context: Context, attrs: AttributeSet?, defStyle: Int) {
        iconsBundle.createIcons(context)
        applyAttr(context, attrs, defStyle)
        tryToEnableIconicsAnimation(iconsBundle.checkedIcon, iconsBundle.uncheckedIcon)
        buttonDrawable = iconsBundle.createStates(context)
    }

    @RestrictTo(LIBRARY_GROUP)
    override fun applyAttr(context: Context, attrs: AttributeSet?, defStyle: Int) {
        IconicsViewsAttrsApplier.readIconicsCompoundButton(context, attrs, iconsBundle)
        iconsBundle.animateChanges = IconicsViewsAttrsApplier.isIconicsAnimateChanges(context, attrs)
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        if (!isInEditMode) {
            super.setText(Iconics.IconicsBuilder().ctx(context).on(text).build(), type)
        } else {
            super.setText(text, type)
        }
    }

    override fun getAccessibilityClassName(): CharSequence {
        return IconicsCompoundButton::class.java.name
    }
}