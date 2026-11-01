# Einkaufsliste – Android-App

Eine native Android-Anwendung zur Verwaltung einer persönlichen Einkaufsliste, entwickelt mit **Java** und **Android Studio**.

Artikel können hinzugefügt, als erledigt oder nicht verfügbar markiert und wieder gelöscht werden. Die Liste wird lokal gespeichert und bleibt auch nach dem Neustart der App erhalten.

## Screenshot

![Einkaufsliste – App-Ansicht](screenshot/Screenshot_01.png)

## Funktionen

- **Artikel hinzufügen:** Neue Einträge über ein Eingabefeld zur Einkaufsliste hinzufügen.
- **Autovervollständigung:** Bereits eingegebene Artikel werden als Vorschläge angeboten.
- **Erledigt markieren:** Eingekaufte Artikel werden durchgestrichen und abgeschwächt dargestellt. Die Markierung lässt sich wieder entfernen.
- **Nicht verfügbar markieren:** Fehlende Artikel werden farblich hervorgehoben und durchgestrichen. Auch dieser Status lässt sich ändern.
- **Artikel löschen:** Einträge können aus der Liste entfernt werden.
- **Dauerhafte Speicherung:** Artikel, Statusänderungen und Löschungen bleiben nach einem Neustart erhalten.
- **Offline nutzbar:** Die App benötigt keine Internetverbindung.

## Verwendete Technologien

| Technologie | Verwendung |
| --- | --- |
| Java | Logik, Ereignisverarbeitung und Verwaltung der Artikel |
| Android SDK | Native Android-Anwendung |
| XML | Gestaltung der Benutzeroberfläche |
| RecyclerView | Darstellung der Einkaufsliste |
| SharedPreferences | Lokale Speicherung der Einkaufsliste und Artikelvorschläge |
| JSON | Speicherung von Artikeltext und Statusinformationen |
| Android Studio / Gradle | Entwicklung und Build-Prozess |

## So funktioniert die App

1. Einen Artikel in das Eingabefeld schreiben und auf **Hinzufügen** tippen.
2. Mit dem **grünen Häkchen (✔)** einen Artikel als erledigt markieren oder die Markierung entfernen.
3. Mit dem **gelben Kreuz (✖)** einen Artikel als nicht verfügbar markieren oder die Markierung entfernen.
4. Mit dem **roten Löschen-Button** einen Artikel aus der Liste entfernen.
5. Die App schließen und erneut öffnen: Die Einkaufsliste und ihre Zustände werden wiederhergestellt.

## Projekt lokal starten

Voraussetzung: **Android Studio** mit eingerichtetem Android SDK sowie einem Android-Emulator oder angeschlossenen Android-Gerät.

Repository klonen:

```bash
git clone https://github.com/thomas-brakutt/Einkaufsliste-App-Android.git
```

Anschließend den geklonten Projektordner in Android Studio öffnen, die **Gradle-Synchronisierung** abschließen lassen und die App über **Run (▶)** starten.

Das Projekt ist für **Android 7.0 (API-Level 24) oder neuer** konfiguriert.

## Projektstruktur

```text
Einkaufsliste-App-Android/
├── app/
│   └── src/main/
│       ├── java/de/tomasino/shoppinglist/
│       │   ├── MainActivity.java      # Benutzeroberfläche und Speicherung
│       │   ├── EinkaufsItem.java      # Datenmodell eines Artikels
│       │   └── ItemAdapter.java       # Anzeige und Bedienung der Liste
│       ├── res/layout/
│       │   ├── activity_main.xml      # Hauptansicht
│       │   └── item_row.xml           # Darstellung eines Listeneintrags
│       └── AndroidManifest.xml
├── screenshots/
│   └── screenshot_1.png
├── gradle/
├── settings.gradle
└── README.md
```

*Die Struktur zeigt die wichtigsten Projektdateien, nicht sämtliche automatisch erzeugten Android-Ressourcen.*

## Technische Umsetzung

Die `MainActivity` verwaltet die Einkaufsliste und lädt beim Start die gespeicherten Artikel. Der `ItemAdapter` stellt die Einträge in einer `RecyclerView` dar und verarbeitet die Aktionen **erledigt**, **nicht verfügbar** und **löschen**.

Bei Änderungen informiert der Adapter die `MainActivity` über einen Callback. Diese speichert die aktuelle Liste als JSON in `SharedPreferences`. Dadurch bleiben sowohl die Artikel als auch ihre Statusinformationen lokal erhalten. Bereits verwendete Artikelnamen werden ebenfalls gespeichert und für die Autovervollständigung genutzt.

## Was ich bei diesem Projekt gelernt habe

- Entwicklung einer nativen Android-App mit Java
- Gestaltung von Oberflächen mit XML-Layouts
- Darstellung dynamischer Listen mit `RecyclerView` und einem eigenen Adapter
- Verarbeitung von Benutzeraktionen mit Event-Listenern
- Austausch von Informationen zwischen Activity und Adapter über einen Callback
- Dauerhafte lokale Speicherung und Wiederherstellung von Daten
- Fehlersuche und Testen im Android-Emulator

## Autor

Entwickelt von [Thomas Brakutt](https://github.com/thomas-brakutt) als Android-Lernprojekt und für das GitHub-Portfolio überarbeitet.
