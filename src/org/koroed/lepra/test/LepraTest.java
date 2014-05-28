package org.koroed.lepra.test;

import org.koroed.lepra.Lepra;
import org.koroed.lepra.content.LepraPost;
import org.koroed.lepra.loader.LepraAsyncContentListLoader;
import org.koroed.lepra.loader.LepraNewContentHandler;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 28.05.2014
 * Time: 12:25
 */
public class LepraTest {

    public static void main(String[] args) throws Exception {
        //Получаем инстанс
        Lepra l = Lepra.getInstance();

        //Пробуем получить состояние пользователя (null - не авторизован)
        System.out.println(l.getLepraStatus());

        //авторизируемся
        System.out.println(l.login("Hutch", "pass", true));

        //еще раз пробуем
        System.out.println(l.getLepraStatus());

        //Получаем чей-нибудь профаил
        System.out.println(l.loadProfile("Fill"));

        //Создаем загрузчик для контента
        //если первый параметр null - загружаем главную, иначе - подлепру
        //для обработки каждого нового поста используется хендлер, чтобы не ждать пока все загрузится.
        LepraAsyncContentListLoader loader = l.getPostListLoader(null, new LepraNewContentHandler<LepraPost>() {
            @Override
            public void processContent(LepraPost content) {
                System.out.println(content);
            }
        });

        // загружаем посты
        loader.load();

        System.out.println("----------");

        // ждем пока загрузка не закончится
        //          (если загрузку остальных постов не дожидаясь завершения предыдущкей,
        //           то предыдущая загрузка будет прервана)
        while(!loader.isDone()) {

        }

        System.out.println(" ^^^^^^^^^ ");

        // загружаем еще посты
        loader.load();

        //Йоваааан!!!
        System.out.println(l.loadProfile("jovan"));

        //все , пойдем отсюда
        l.logout();
    }
}
