package com.github.zharovvv.kmemes.core.architecture.elm.optin

@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "Данный конструктор следует использовать аккуратно. " +
            "Если ожидается, что для получения начального состояния нужно много времени, " +
            "то лучше сделать какое-нибудь загрузочное начальное состояние, " +
            "а затем получать новое состояние через Actor."
)
annotation class DelicateElmViewModelConstructor