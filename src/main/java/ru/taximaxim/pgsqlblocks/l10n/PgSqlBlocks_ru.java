package ru.taximaxim.pgsqlblocks.l10n;

import java.util.ListResourceBundle;

public class PgSqlBlocks_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"confirm_action", "Подтверждение действия"},
                {"exit_confirm_message", "Вы действительно хотите выйти из pgSqlBlocks?"},
                {"delete_confirm_message", "Вы действительно хотите удалить {0}?"},
                {"kill_process_confirm_message", "Вы действительно хотите уничтожить процесс {0}?"},
                {"cancel_process_confirm_message", "Вы действительно хотите послать сигнал отмены процесса {0}?"},
                {"system_tray_not_available_message", "Системный трей недоступен"},
                {"about", "&О приложении"},
                {"exit", "&Выход"},
                {"current_activity", "Текущая активность"},
                {"database", "Базы данных"},
                {"kill_process", "Уничтожить процесс"},
                {"cancel_process", "Послать сигнал отмены процесса"},
                {"lock_history", "История блокировок"},
                {"lock_saved", "Блокировка сохранена..."},
                {"no_locks", "Не найдено блокировок для сохранения"},
                {"view_lock_history", "Открыть историю блокировок"},
                {"one_db_has_lock", "В одной имеется блокировка"},
                {"several_db_has_lock", "В нескольких имеется блокировка"},
                {"error_reading_the_manifest", "Ошибка при чтении манифеста"},
                {"process_terminated", "{0}  pid={1} остановлен."},
                {"process_not_terminated", "{0}  pid={1} не удалось остановить."},
                {"process_cancelled", "{0}  pid={1} отменен."},
                {"process_not_cancelled", "{0}  pid={1} не удалось отменить."},
                {"remove_dbcdata_on_disconnect", "Удалить dbcData при отключении \"{0}\" из списка updaterList"},
                {"db_already_connected", "{0} соединение уже создано."},
                {"db_connecting", "Соединение {0}..."},
                {"db_connected", "{0} Соединение создано."},
                {"db_disconnected", "{0} Соединение закрыто."},
                {"error_on_check_is_connected", "Ошибка при попытке получения значения \"isConnected\": {0}"},
                {"db_exists_in_conf_file", "Данное БД уже есть в конфигурационном файле"},
                {"db_update_error", "Ошибка в DbcData: {0}."},
                {"db_updating", "Обновление \"{0}\"..."},
                {"db_finish_updating", "Обновление завершено \"{0}\"."},
                {"db_error_on_connect", "Ошибка при обновлении DbcData: {0}"},

                // action icons
                {"add_db", "Добавить БД"},
                {"delete_db", "Удалить БД"},
                {"edit_db", "Редактировать БД"},
                {"connect", "Подключиться"},
                {"disconnect", "Отключиться"},
                {"update", "Обновить"},
                {"autoupdate", "Автообновление"},
                {"view_only_blocked", "Показывать только блокирующие и блокированные процессы"},
                {"save_blocks", "Выгрузить историю блокировок"},
                {"open_blocks", "Открыть файл с историей блокировок"},
                {"settings", "Настройки"},
                {"process_filter", "Фильтр"},
                {"cancel_update", "Отменить обновление"},
                {"show_logs_panel", "Скрыть панель логов"},
                {"hide_logs_panel", "Отображать панель логов"},
                {"default_action", "Действие по-умолчанию"},

                // columns
                {"pid", "PID"},
                {"num_of_blocked_processes", "Блокирует"},
                {"application", "Приложение"},
                {"db_name", "Имя БД"},
                {"user_name", "Роль"},
                {"client", "Данные клиента"},
                {"backend_start", "Подключение к серверу"},
                {"query_start", "Старт запроса"},
                {"xact_start", "Старт транзакции"},
                {"state", "Состояние"},
                {"state_change", "Изменено"},
                {"blocked_by", "Кем блокируется"},
                {"lock_type", "Тип объекта блокировки"},
                {"relation", "Объект блокировки"},
                {"query", "Запрос"},
                {"slow_query", "Долгий процесс"},
                {"undefined", "Без имени"},

                // settings dialog
                {"processes", "Процессы"},
                {"auto_update_interval", "Период автообновления"},
                {"show_idle_process", "Показывать idle процессы"},
                {"show_pgSqlBlock_process", "Показывать собственные запросы среди процессов"},
                {"notifications", "Уведомления"},
                {"show_tray_notifications", "Показывать оповещения о блокировках в трее"},
                {"prompt_confirmation_on_process_kill", "Подтверждать отмену/уничтожение процесса"},
                {"prompt_confirmation_on_program_close", "Подтверждать при выходе из pgSqlBlocks"},
                {"columns", "Колонки"}
        };
    }
}
