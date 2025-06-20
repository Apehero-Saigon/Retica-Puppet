package com.photo.editor.monetization.terasoft

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photo.editor.data.repository.SettingsRepository
import com.photo.editor.domain.model.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import javax.inject.Inject

@KoinViewModel
class TerasoftViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    var selectedLanguage: Language? by mutableStateOf(null)

    fun confirmLanguage() {
        selectedLanguage?.let {
            settingsRepository.setLanguage(it)
        }
    }

    fun setOnboarded() {
        settingsRepository.setOnboarded()
    }

    fun setLanguageWithCode(code: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val language = Language.entries.find { it.code == code } ?: Language.English

            settingsRepository.setLanguage(language)
        }
    }
}