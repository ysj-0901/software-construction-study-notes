# Week 4 — Persistent Data

### 1. What is persistent data?

**Persistent data** is data that survives after a program stops running.

Typical purposes include:

- saving and restoring application state
- logging information
- storing data so it can be reused later
- keeping important information permanently

Persistent data can be stored in:

- files — JSON, XML, snapshots, images, etc.
- databases — SQLite, MySQL, Cassandra, etc.

The core idea is:

> **Working-memory data is temporary; persistent data survives program termination/restart.**


### 2. Different kinds of persistent storage formats

The slides distinguish several categories:

|Category|Examples|
|---|---|
|Unstructured text|raw text, `.doc`|
|Structured text|CSV, TSV, DIMACS CNF, bespoke formats|
|Graphics|PNG, JPEG, BMP, FITS|
|Audio/video|MP3, WAV, MP4|
|Compression|gzip, tar, rar|

Structured text files are useful when the data has some predictable organisation, such as spreadsheets or sensor data.

### 3. Choosing a persistence format is a **design decision**

There is no single “best” format.

You should choose based on:

- what the application does
- what kind of data it stores
- restrictions such as:
    - storage size
    - performance / rapid access
    - licensing
    - development effort

So:

Application requirements

        ↓

Data characteristics

        ↓

Constraints

        ↓

Choose persistence format

### 4. Important qualities when choosing a format

#### Programming agility

How easy is it to implement and work with?

A simple format may mean less development overhead.

#### Extensibility

Can the format evolve easily?

For example:

name,age

Alice,20

What happens later if you need:

name,age,email,address,phone,...

Some formats handle structural changes better than others.

#### Portability

Ask:

- Can another application read the data?
- Can another programming language use it?
- Can it work on different hardware/platforms?

#### Maintainability

Think about the whole software lifecycle:

- How long will the software exist?
- Who will maintain it?
- Will the chosen data format still be understandable/manageable later?

This is particularly important in COMP6442: **the easiest solution to write today is not necessarily the easiest solution to maintain later.**

---

### 5. Robustness

The format should have a clear and reliable structure.

The slides compare formats such as:

Bespoke ↔ XML ↔ JSON

One concern with poorly specified formats is the lack of a schema or clear structure:

How do we know whether the file is valid?

How do different programs interpret it consistently?

Poorly defined formats can therefore create **interoperability problems**.

---

### 6. Size vs completeness

Another design trade-off is:

smaller file                 complete information

     ←──── Lossy vs Lossless ────→

**Lossy** storage/compression discards some information.

That can be acceptable for:

- images
- audio

But potentially unacceptable for:

- financial data
- scientific data

because losing even small amounts of information may change the meaning of the data.

---

### 7. Internationalisation

Consider who will use the data.

For text encoding:

ASCII

vs

UTF-8

UTF-8 supports a much wider range of characters and is therefore much more suitable for international data.

---

### 8. Relational databases

For **large volumes of structured data**, a DBMS can be more appropriate than storing everything in files.

Relational databases such as MySQL allow related data to be stored in separate tables and linked using **unique identifiers**.

A key advantage is avoiding unnecessary repetition of data.
