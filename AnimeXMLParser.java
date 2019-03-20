public class StackOverflowXmlParser {
    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "ann");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("anime")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

        public static class Entry {
        public final String title;
        public final String photo;
        public final String summary;
        public final Sting vintage;
        public final String genre;
        public final String runTime;

        private Entry(String title, String summary, String photo, String vintage, String genre, String runTime) {
            this.title = title;
            this.summary = summary;
            this.photo = photo;
            this.vintage = vintage;
            this.genre = genre;
            this.runTime = runTime
        }

        public void addNumGenre()
        {
            this.numGenres++;
        }

        public void addGenre(String genre)
        {
            genres[numGenres] = genre;
            addNumGenre();
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "anime");
        String title = null;
        String summary = null;
        String photo = null;
        String vintage = null;
        String genre = null;
        String runTime = null;
        Int gotGenre = 0;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            String type = parser.getAttributeValue(null, "type");
            if (type.equals("Picture")) {
                picture = readPicture(parser);
            } else if (type.equals("Main title")) {
                title = readBlock(parser);
            } else if (type.equals("Genres")) {
                if (gotGenre == 0) {
                    genre = readBlock(parser);
                    gotGenre = 1;
                }
            } else if (type.equals("Plot Summary")) {
                summary = readBlock(parser);
            } else if (type.equals("Running time")) {
                runTime = readBlock(parser);
            } else if (type.equals("Vintage")) {
                vintage = readBlock(parser);
            } else {
                skip(parser);
            }
        }
        return new Entry (title, summary, photo, vintage, genre, runTime);
    }

    // Processes title tags in the feed.
    private String readBlock(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "info");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "info");
        return title;
    }

    // Processes link tags in the feed.
    private String readPicture(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        if (tag.equals("info")) {
                link = parser.getAttributeValue(null, "src");
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
}