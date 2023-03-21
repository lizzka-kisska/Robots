package log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 * 2. Этот класс хранит активные сообщения лога, но в такой реализации он 
 * их лишь накапливает. Надо же, чтобы количество сообщений в логе было ограничено 
 * величиной m_iQueueLength (т.е. реально нужна очередь сообщений 
 * ограниченного размера) 
 */
public class LogWindowSource
{
    private final int m_iQueueLength; //размер массива

    private final ArrayList<LogEntry> m_messages; //массив списков типа LogEntry(принимает уровень лога, соо)
    private final ArrayList<LogChangeListener> m_listeners; //массив списков типа касса в котором метод onLogChanged()
    private volatile LogChangeListener[] m_activeListeners; //volatile - для многопоточности, чтобы операция была атомарная,
    // и инфа не заносилась в кэш
    
    public LogWindowSource(int iQueueLength) 
    {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayList<>(iQueueLength); //создаем новый массив const = длины массива 100
        m_listeners = new ArrayList<>(); //создаем массив слушателей
    }
    
    public void registerListener(LogChangeListener listener)
    {
        synchronized(m_listeners) //запрещаем вход в эту часть кода и в массив слушателей >1 потокам
        {
            m_listeners.add(listener); //добавляем в массив слушателей слушателя
            m_activeListeners = null; //активных слуш видимо больше нет, так как мы их занесли в массив
        }
    }
    
    public void unregisterListener(LogChangeListener listener) //убираем незарегистрированного слушателя
    {
        synchronized(m_listeners)
        {
            m_listeners.remove(listener);
            m_activeListeners = null;
        }
    }
    
    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage); //какой-то вход в лог
        synchronized (m_messages) { //чтобы не было конкуренции при вычислении длины массива и добавлении
            if (m_messages.size() >= m_iQueueLength) { //ограничиваем величиной
                m_messages.remove(0);
            }
            m_messages.add(entry); //добавляем в массив сообщений вход в лог
        }

        LogChangeListener[] activeListeners = m_activeListeners; //массив из интерфейса???? накопленных активных слуш
        if (activeListeners == null) //если массив пуст то
        {
            synchronized (m_listeners)
            {
                if (m_activeListeners == null)
                {
                    activeListeners = m_listeners.toArray(new LogChangeListener [0]); //добавляем в общий массив то
                    // что накопилось
                    //мб тут нужно написать m_listeners убрать
                    m_activeListeners = activeListeners; //меняем местами я не ебу зачем
                }
            }
        }
        assert activeListeners != null;
        for (LogChangeListener listener : activeListeners) //переменные типа интерфейса в activeListeners
        {
            listener.onLogChanged();//применяем метод из интерфейса
        }
    }
    
    public int size()
    {
        return m_messages.size();
    } //метод - размер массива списков сообщений

    public Iterable<LogEntry> range(int startFrom, int count) //делаем какойто итератор по входу в лог видимо
    // для слайса
    {
        if (startFrom < 0 || startFrom >= m_messages.size()) //выход за диапазон
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_messages.size());
        return m_messages.subList(startFrom, indexTo);//возваращаем слайс списка
    }

    public Iterable<LogEntry> all()
    {
        return m_messages;
    }//возваращаем массив списков сообщений
}
