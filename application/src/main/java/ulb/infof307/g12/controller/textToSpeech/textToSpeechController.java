package ulb.infof307.g12.controller.textToSpeech;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import ulb.infof307.g12.controller.javafx.connexion.MenuPrincipal;


public class textToSpeechController {

    /**
     * @param text : texte à dire
     * permet de dire le texte vocalement
     */
    public void synthese_vocal(String text){
        try {
            //setting properties as Kevin Dictionary
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
            //Register Engine
            VoiceManager voiceManager = VoiceManager.getInstance();
            //Access Voice
            Voice voice = voiceManager.getVoice("kevin");
            //Setting the Voice speed
            voice.setRate(125);
            // loads the Voice
            voice.allocate();
            // start talking
            voice.speak(text);
            // unload the Voice
            voice.deallocate();
        } catch (Exception e) {
            MenuPrincipal.getINSTANCE().showErrorPopup("Impossible de dire le texte");
        }
    }
}
