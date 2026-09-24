package com.example.data

import com.example.model.Quote

object QuoteSeedData {
    val initialQuotes: List<Quote> = listOf(
        // ==========================================
        // 1. SUCCESS (सफलता)
        // ==========================================
        Quote(
            id = 1,
            text = "Dream is not that which you see while sleeping, it is something that does not let you sleep.",
            author = "Dr. A.P.J. Abdul Kalam",
            category = "SUCCESS",
            hindiText = "सपने वो नहीं जो हम सोते हुए देखते हैं, सपने वो हैं जो हमें सोने नहीं देते।",
            tags = "dreams,hardwork,dedication"
        ),
        Quote(
            id = 2,
            text = "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            author = "Winston Churchill",
            category = "SUCCESS",
            hindiText = "सफलता अंतिम नहीं है, असफलता घातक नहीं है: जारी रखने का साहस ही मायने रखता है।",
            tags = "perseverance,courage"
        ),
        Quote(
            id = 3,
            text = "The way to get started is to quit talking and begin doing.",
            author = "Walt Disney",
            category = "SUCCESS",
            hindiText = "शुरुआत करने का तरीका है बातें बंद करना और काम शुरू करना।",
            tags = "action,initiation"
        ),
        Quote(
            id = 4,
            text = "If you want to shine like a sun, first burn like a sun.",
            author = "Dr. A.P.J. Abdul Kalam",
            category = "SUCCESS",
            hindiText = "अगर आप सूरज की तरह चमकना चाहते हैं, तो पहले सूरज की तरह जलना सीखें।",
            tags = "sacrifice,excellence"
        ),
        Quote(
            id = 5,
            text = "Opportunities don't happen. You create them.",
            author = "Chris Grosser",
            category = "SUCCESS",
            hindiText = "अवसर खुद नहीं बनते, उन्हें आपको खुद बनाना पड़ता है।",
            tags = "opportunity,effort"
        ),
        Quote(
            id = 6,
            text = "Don't watch the clock; do what it does. Keep going.",
            author = "Sam Levenson",
            category = "SUCCESS",
            hindiText = "घड़ी मत देखो; वही करो जो वो करती है। निरंतर चलते रहो।",
            tags = "time,persistence"
        ),
        Quote(
            id = 7,
            text = "The secret of getting ahead is getting started.",
            author = "Mark Twain",
            category = "SUCCESS",
            hindiText = "आगे बढ़ने का सबसे बड़ा रहस्य है शुरुआत कर देना।",
            tags = "momentum,start"
        ),
        Quote(
            id = 8,
            text = "Hard work beats talent when talent fails to work hard.",
            author = "Tim Notke",
            category = "SUCCESS",
            hindiText = "कड़ी मेहनत प्रतिभा को हरा देती है जब प्रतिभा कड़ी मेहनत नहीं करती।",
            tags = "hardwork,talent"
        ),
        Quote(
            id = 9,
            text = "Success usually comes to those who are too busy to be looking for it.",
            author = "Henry David Thoreau",
            category = "SUCCESS",
            hindiText = "सफलता आमतौर पर उन लोगों के पास आती है जो इसकी तलाश करने में बहुत व्यस्त होते हैं।",
            tags = "focus,work"
        ),
        Quote(
            id = 10,
            text = "Action is the foundational key to all success.",
            author = "Pablo Picasso",
            category = "SUCCESS",
            hindiText = "कर्म ही समस्त सफलता की मूलभूत कुंजी है।",
            tags = "action,consistency"
        ),
        Quote(
            id = 11,
            text = "Success is walking from failure to failure with no loss of enthusiasm.",
            author = "Winston Churchill",
            category = "SUCCESS",
            hindiText = "उत्साह खोए बिना एक विफलता से दूसरी विफलता की ओर बढ़ना ही सफलता है।",
            tags = "resilience,attitude"
        ),
        Quote(
            id = 12,
            text = "I never dreamed about success. I worked for it.",
            author = "Estée Lauder",
            category = "SUCCESS",
            hindiText = "मैंने कभी सफलता के सपने नहीं देखे, मैंने इसके लिए काम किया।",
            tags = "action,grit"
        ),

        // ==========================================
        // 2. STUDY & FOCUS (पढ़ाई और एकाग्रता)
        // ==========================================
        Quote(
            id = 13,
            text = "Education is the most powerful weapon which you can use to change the world.",
            author = "Nelson Mandela",
            category = "STUDY",
            hindiText = "शिक्षा सबसे शक्तिशाली हथियार है जिसका उपयोग आप दुनिया को बदलने के लिए कर सकते हैं।",
            tags = "education,power"
        ),
        Quote(
            id = 14,
            text = "Cultivation of mind should be the ultimate aim of human existence.",
            author = "Dr. B.R. Ambedkar",
            category = "STUDY",
            hindiText = "मस्तिष्क का विकास मानव अस्तित्व का अंतिम लक्ष्य होना चाहिए।",
            tags = "intellect,growth"
        ),
        Quote(
            id = 15,
            text = "Live as if you were to die tomorrow. Learn as if you were to live forever.",
            author = "Mahatma Gandhi",
            category = "STUDY",
            hindiText = "ऐसे जियो जैसे कल मरना हो। ऐसे सीखो जैसे हमेशा जीना हो।",
            tags = "learning,knowledge"
        ),
        Quote(
            id = 16,
            text = "The beautiful thing about learning is that no one can take it away from you.",
            author = "B.B. King",
            category = "STUDY",
            hindiText = "सीखने की सबसे खूबसूरत बात यह है कि इसे आपसे कोई छीन नहीं सकता।",
            tags = "wisdom,strength"
        ),
        Quote(
            id = 17,
            text = "An investment in knowledge pays the best interest.",
            author = "Benjamin Franklin",
            category = "STUDY",
            hindiText = "ज्ञान में किया गया निवेश सबसे अच्छा ब्याज देता है।",
            tags = "investment,mind"
        ),
        Quote(
            id = 18,
            text = "There are no shortcuts to any place worth going.",
            author = "Beverly Sills",
            category = "STUDY",
            hindiText = "सार्थक मुकाम तक पहुंचने का कोई छोटा रास्ता नहीं होता।",
            tags = "discipline,focus"
        ),
        Quote(
            id = 19,
            text = "Discipline is the bridge between goals and accomplishment.",
            author = "Jim Rohn",
            category = "STUDY",
            hindiText = "अनुशासन ही लक्ष्यों और उपलब्धियों के बीच का सेतु है।",
            tags = "discipline,goals"
        ),
        Quote(
            id = 20,
            text = "The expert in anything was once a beginner.",
            author = "Helen Hayes",
            category = "STUDY",
            hindiText = "हर विशेषज्ञ कभी न कभी एक नौसिखिया ही था।",
            tags = "practice,patience"
        ),
        Quote(
            id = 21,
            text = "Focus is a muscle. The more you practice saying no to distractions, the stronger it gets.",
            author = "Robin Sharma",
            category = "STUDY",
            hindiText = "एकाग्रता एक मांसपेशी की तरह है। जितना आप भटकावों को 'ना' कहेंगे, यह उतनी मजबूत बनेगी।",
            tags = "focus,habit"
        ),
        Quote(
            id = 22,
            text = "Today's preparation determines tomorrow's achievement.",
            author = "Anonymous",
            category = "STUDY",
            hindiText = "आज की तैयारी ही कल की उपलब्धि तय करती है।",
            tags = "preparation,exam"
        ),

        // ==========================================
        // 3. LIFE LESSONS (जीवन सूत्र)
        // ==========================================
        Quote(
            id = 23,
            text = "Arise, awake, and stop not till the goal is reached.",
            author = "Swami Vivekananda",
            category = "LIFE",
            hindiText = "उठो, जागो और तब तक मत रुको जब तक लक्ष्य प्राप्त न हो जाए।",
            tags = "purpose,strength"
        ),
        Quote(
            id = 24,
            text = "In the middle of every difficulty lies opportunity.",
            author = "Albert Einstein",
            category = "LIFE",
            hindiText = "हर कठिनाई के बीच में अवसर छुपा होता है।",
            tags = "optimism,resilience"
        ),
        Quote(
            id = 25,
            text = "Life is 10% what happens to you and 90% how you react to it.",
            author = "Charles R. Swindoll",
            category = "LIFE",
            hindiText = "जिंदगी 10% इस बात से बनती है कि क्या होता है और 90% इस बात से कि आप उस पर क्या प्रतिक्रिया देते हैं।",
            tags = "attitude,mindset"
        ),
        Quote(
            id = 26,
            text = "The best way to predict the future is to create it.",
            author = "Peter Drucker",
            category = "LIFE",
            hindiText = "भविष्य की भविष्यवाणी करने का सबसे अच्छा तरीका इसे स्वयं बनाना है।",
            tags = "destiny,courage"
        ),
        Quote(
            id = 27,
            text = "Never let your memories be greater than your dreams.",
            author = "Douglas Ivester",
            category = "LIFE",
            hindiText = "अपनी यादों को अपने सपनों से बड़ा कभी मत बनने दो।",
            tags = "future,hope"
        ),
        Quote(
            id = 28,
            text = "You only live once, but if you do it right, once is enough.",
            author = "Mae West",
            category = "LIFE",
            hindiText = "आप केवल एक बार जीते हैं, लेकिन अगर सही से जिएं तो एक बार ही काफी है।",
            tags = "life,living"
        ),
        Quote(
            id = 29,
            text = "Peace comes from within. Do not seek it without.",
            author = "Gautama Buddha",
            category = "LIFE",
            hindiText = "शांति भीतर से आती है। इसे बाहर मत खोजो।",
            tags = "serenity,truth"
        ),
        Quote(
            id = 30,
            text = "Difficult roads often lead to beautiful destinations.",
            author = "Zig Ziglar",
            category = "LIFE",
            hindiText = "कठिन रास्ते अक्सर सबसे खूबसूरत मंजिलों की ओर ले जाते हैं।",
            tags = "patience,destiny"
        ),

        // ==========================================
        // 4. CAREER & GROWTH (करियर और लक्ष्य)
        // ==========================================
        Quote(
            id = 31,
            text = "Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work.",
            author = "Steve Jobs",
            category = "CAREER",
            hindiText = "आपका काम आपके जीवन का एक बड़ा हिस्सा भरेगा, और संतुष्ट होने का एकमात्र तरीका यह है कि आप वह करें जिसे आप महान काम मानते हैं।",
            tags = "passion,excellence"
        ),
        Quote(
            id = 32,
            text = "Take chances, take calculated risks, and continue to be open to change.",
            author = "Sundar Pichai",
            category = "CAREER",
            hindiText = "अवसर तलाशें, परखे हुए जोखिम लें और बदलाव के प्रति हमेशा खुले रहें।",
            tags = "growth,innovation"
        ),
        Quote(
            id = 33,
            text = "I don't believe in taking right decisions. I take decisions and then make them right.",
            author = "Ratan Tata",
            category = "CAREER",
            hindiText = "मैं सही निर्णय लेने में विश्वास नहीं करता। मैं निर्णय लेता हूँ और फिर उन्हें सही साबित करता हूँ।",
            tags = "confidence,leadership"
        ),
        Quote(
            id = 34,
            text = "The only limit to our realization of tomorrow will be our doubts of today.",
            author = "Franklin D. Roosevelt",
            category = "CAREER",
            hindiText = "कल की हमारी सफलताओं की एकमात्र सीमा आज के हमारे संदेह होंगे।",
            tags = "confidence,doubt"
        ),
        Quote(
            id = 35,
            text = "Choose a job you love, and you will never have to work a day in your life.",
            author = "Confucius",
            category = "CAREER",
            hindiText = "ऐसा पेशा चुनें जिसे आप प्यार करते हैं, और आपको जीवन में एक भी दिन काम नहीं करना पड़ेगा।",
            tags = "career,love"
        ),
        Quote(
            id = 36,
            text = "Price is what you pay. Value is what you get.",
            author = "Warren Buffett",
            category = "CAREER",
            hindiText = "कीमत वह है जो आप चुकाते हैं। मूल्य वह है जो आप प्राप्त करते हैं।",
            tags = "value,wisdom"
        ),
        Quote(
            id = 37,
            text = "Stay hungry, stay foolish.",
            author = "Steve Jobs",
            category = "CAREER",
            hindiText = "हमेशा भूखे रहो (सीखने के लिए), हमेशा नासमझ रहो (नई बातें जानने के लिए)।",
            tags = "curiosity,growth"
        ),
        Quote(
            id = 38,
            text = "Be so good they can't ignore you.",
            author = "Steve Martin",
            category = "CAREER",
            hindiText = "इतने काबिल बनो कि लोग आपको नज़रअंदाज़ ही न कर सकें।",
            tags = "skills,mastery"
        ),

        // ==========================================
        // 5. HAPPINESS & PEACE (खुशी और शांति)
        // ==========================================
        Quote(
            id = 39,
            text = "Happiness is not something readymade. It comes from your own actions.",
            author = "Dalai Lama",
            category = "HAPPINESS",
            hindiText = "खुशी कोई बनी-बनाई चीज नहीं है। यह आपके अपने कर्मों से आती है।",
            tags = "happiness,karma"
        ),
        Quote(
            id = 40,
            text = "Smile, breathe and go slowly. There is no rush in living deeply.",
            author = "Thich Nhat Hanh",
            category = "HAPPINESS",
            hindiText = "मुस्कुराएं, गहरी सांस लें और सहजता से आगे बढ़ें। गहराई से जीने में कोई जल्दबाजी नहीं है।",
            tags = "peace,mindfulness"
        ),
        Quote(
            id = 41,
            text = "Count your age by friends, not years. Count your life by smiles, not tears.",
            author = "John Lennon",
            category = "HAPPINESS",
            hindiText = "अपनी उम्र दोस्तों से गिनें, वर्षों से नहीं। अपने जीवन को मुस्कुराहटों से नापें, आंसुओं से नहीं।",
            tags = "joy,gratitude"
        ),
        Quote(
            id = 42,
            text = "Gratitude turns what we have into enough, and more.",
            author = "Melody Beattie",
            category = "HAPPINESS",
            hindiText = "कृतज्ञता (धन्यवाद भाव) जो हमारे पास है उसे पर्याप्त और उससे भी अधिक बना देती है।",
            tags = "gratitude,contentment"
        ),
        Quote(
            id = 43,
            text = "The most wasted of days is one without laughter.",
            author = "E.E. Cummings",
            category = "HAPPINESS",
            hindiText = "हंसी और खुशी के बिना बिताया गया दिन सबसे व्यर्थ दिन है।",
            tags = "laughter,joy"
        ),
        Quote(
            id = 44,
            text = "Happiness is when what you think, what you say, and what you do are in harmony.",
            author = "Mahatma Gandhi",
            category = "HAPPINESS",
            hindiText = "खुशी तब मिलती है जब आपके विचार, आपके शब्द और आपके कर्म पूरी तरह एक सुर में हों।",
            tags = "harmony,truth"
        ),

        // ==========================================
        // 6. MORNING INSPIRATION (दैनिक जागरण)
        // ==========================================
        Quote(
            id = 45,
            text = "Every morning starts a new page in your story. Make it a great one today.",
            author = "Doe Zantamata",
            category = "MORNING",
            hindiText = "हर सुबह आपकी कहानी का एक नया पन्ना शुरू करती है। आज इसे बेहतरीन बनाएं।",
            tags = "morning,freshstart"
        ),
        Quote(
            id = 46,
            text = "When you arise in the morning think of what a precious privilege it is to be alive.",
            author = "Marcus Aurelius",
            category = "MORNING",
            hindiText = "सुबह जब आप जागें, तो सोचें कि जीवित होना, सोचना और प्रेम करना कितना अनमोल उपहार है।",
            tags = "gratitude,morning"
        ),
        Quote(
            id = 47,
            text = "Today is not just another day, but another possible chance to achieve what you couldn't achieve yesterday.",
            author = "Daily Rise",
            category = "MORNING",
            hindiText = "आज केवल एक और दिन नहीं है, बल्कि वह सब हासिल करने का एक सुनहरा अवसर है जो कल अधूरा रह गया था।",
            tags = "hope,newday"
        ),
        Quote(
            id = 48,
            text = "Rise up, start fresh, see the bright opportunity in each new day.",
            author = "Daily Rise",
            category = "MORNING",
            hindiText = "उठिए, एक नई शुरुआत कीजिए, और हर नए दिन में छिपे चमकीले अवसर को देखिए।",
            tags = "energy,morning"
        ),

        // ==========================================
        // VIP BONUS QUOTES (Unlocked with Rewarded Ad)
        // ==========================================
        Quote(
            id = 49,
            text = "Mastering others is strength. Mastering yourself is true power.",
            author = "Lao Tzu",
            category = "SUCCESS",
            hindiText = "दूसरों पर विजय पाना बल है, स्वयं पर विजय पाना ही सच्ची शक्ति है।",
            tags = "power,selfcontrol",
            isVip = true,
            isUnlocked = false
        ),
        Quote(
            id = 50,
            text = "The mind is everything. What you think you become.",
            author = "Gautama Buddha",
            category = "STUDY",
            hindiText = "मन ही सब कुछ है। आप जैसा सोचते हैं, वैसे ही बन जाते हैं।",
            tags = "mindset,thoughts",
            isVip = true,
            isUnlocked = false
        ),
        Quote(
            id = 51,
            text = "Simplicity is the ultimate sophistication.",
            author = "Leonardo da Vinci",
            category = "LIFE",
            hindiText = "सादगी ही परम परिष्कार है।",
            tags = "simplicity,peace",
            isVip = true,
            isUnlocked = false
        ),
        Quote(
            id = 52,
            text = "Great things never come from comfort zones.",
            author = "Anonymous",
            category = "CAREER",
            hindiText = "महान उपलब्धियां कभी भी आरामदायक दायरे (कंफर्ट ज़ोन) में रहकर नहीं मिलतीं।",
            tags = "growth,courage",
            isVip = true,
            isUnlocked = false
        ),
        Quote(
            id = 53,
            text = "Wherever you go, go with all your heart.",
            author = "Confucius",
            category = "HAPPINESS",
            hindiText = "आप जहाँ भी जाएँ, पूरे दिल और सच्ची निष्ठा के साथ जाएँ।",
            tags = "passion,heart",
            isVip = true,
            isUnlocked = false
        )
    )
}
