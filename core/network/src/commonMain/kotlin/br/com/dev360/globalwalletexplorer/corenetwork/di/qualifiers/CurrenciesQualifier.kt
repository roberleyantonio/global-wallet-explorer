package br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object CurrenciesQualifier: Qualifier {
    override val value: QualifierValue
        get() = "CurrenciesQualifier"
}