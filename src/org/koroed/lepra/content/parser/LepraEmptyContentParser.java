package org.koroed.lepra.content.parser;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 12:51
 */
public class LepraEmptyContentParser extends LepraContentParser<Object> {

    private static LepraEmptyContentParser instance = new LepraEmptyContentParser();

    public static LepraEmptyContentParser getInstance() {
        return instance;
    }

    private LepraEmptyContentParser() {
    }

    @Override
    public Object parseContent(String content) {
        return null;
    }


}
